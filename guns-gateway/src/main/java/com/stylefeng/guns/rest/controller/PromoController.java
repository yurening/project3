package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.util.concurrent.RateLimiter;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.core.exception.ServiceExceptionEnum;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.consistant.RedisPrefixConsistant;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.service.CinemaTestService;
import com.stylefeng.guns.rest.service.PromoService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.PromoVO;
import com.stylefeng.guns.rest.vo.UserVO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
@ResponseBody
@RequestMapping("promo")
public class PromoController {
    @Reference(interfaceClass = PromoService.class,check = false)
    PromoService promoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    private ExecutorService executorService;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(100);
        rateLimiter = RateLimiter.create(100);
    }

    @RequestMapping("getPromo")
    public BaseResVO getPromo(Integer brandId,String hallType,
                              Integer areaId,Integer pageSize,Integer nowPage){
        BaseResVO promo = promoService.getPromo(brandId, hallType, areaId, pageSize, nowPage);
        return promo;
    }

    @RequestMapping("createOrder")
    public BaseResVO createOrder(Integer promoId ,Integer amount, String promoToken, HttpServletRequest request) {
        rateLimiter.acquire();
        if (amount <= 0 || amount > 5) {
            return BaseResVO.fail(1, "下单数量异常");
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(authToken);
        Integer userId = userVO.getUuid();
        String tokenKey = String.format(RedisPrefixConsistant.PROMO_USER_TOKEN_PROMOID, promoId, userId);
        if (!redisTemplate.hasKey(tokenKey)) {
            return BaseResVO.fail(1, "令牌失效");
        }
        String tokenInRedis = (String) redisTemplate.opsForValue().get(tokenKey);
        if (!tokenInRedis.equals(promoToken)) {
            return BaseResVO.fail(1, "令牌失效");
        }
        Future<Boolean> future = executorService.submit(() -> {
            String stockLogId = promoService.initPromoStockLog(promoId, amount);
            if (StringUtils.isBlank(stockLogId)) {
                throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
            }
            return promoService.createPromo(promoId, amount, userId, stockLogId);
        });
        try {
            if(future.get()) {
                return BaseResVO.fail(0, "下单成功");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new GunsException(GunsExceptionEnum.FUTURE_ERROR);
        }
        return BaseResVO.fail(1, "下单失败，请稍后重试");
    }

    @RequestMapping("publishPromoStock")
    public BaseResVO publishPromoStock() {
        promoService.publishPromoStock();
        return BaseResVO.fail(0, "发布成功");
    }

    @RequestMapping("generateToken")
    public BaseResVO generateToken(Integer promoId, HttpServletRequest request) {
        PromoVO promoVO = promoService.getPromoById(promoId);
        if (promoVO == null || promoVO.getStatus() != 1) {
            return BaseResVO.fail(1, "活动失效");
        }
        String soldoutKey = RedisPrefixConsistant.PROMO_SOLDOUT_PROMOID + promoId;
        if (redisTemplate.hasKey(soldoutKey)) {
            return BaseResVO.fail(1, "活动太火爆啦");
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(authToken);
        String token = promoService.generateToken(promoId, userVO.getUuid());
        if (token == null) {
            return BaseResVO.fail(1, "活动太火爆啦");
        }
        return BaseResVO.fail(0,token);
    }
}
