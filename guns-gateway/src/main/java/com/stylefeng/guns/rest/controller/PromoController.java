package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.core.exception.ServiceExceptionEnum;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.service.PromoService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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


    @RequestMapping("getPromo")
    public BaseResVO getPromo(Integer brandId,String hallType,
                              Integer areaId,Integer pageSize,Integer nowPage){
        BaseResVO promo = promoService.getPromo(brandId, hallType, areaId, pageSize, nowPage);
        return promo;
    }

    @RequestMapping("createOrder")
    public BaseResVO createOrder(Integer promoId ,Integer amount, HttpServletRequest request) {
        if (amount <= 0 || amount > 5) {
            return BaseResVO.fail(1, "下单数量异常");
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(authToken);
        Integer userID = userVO.getUuid();
        String stockLogId = promoService.initPromoStockLog(promoId, amount);

        if (StringUtils.isBlank(stockLogId)) {
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }
        Boolean result = promoService.createPromo(promoId, amount, userID, stockLogId);
        if(result) {
            return BaseResVO.fail(0, "下单成功");
        }
        return BaseResVO.fail(1, "下单失败，请稍后重试");
    }

    @RequestMapping("publishPromoStock")
    public BaseResVO publishPromoStock() {
        promoService.publishPromoStock();
        return BaseResVO.fail(0, "发布成功");
    }

    @RequestMapping("generateToken")
    public BaseResVO generateToken() {
        return BaseResVO.fail(0,"");
    }
}
