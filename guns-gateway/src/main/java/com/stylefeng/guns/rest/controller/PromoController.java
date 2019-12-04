package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.service.PromoService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
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
    public BaseResVO createOrder(Integer promoId ,Integer amount, HttpServletRequest request){
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(authToken);
        Integer uuid = userVO.getUuid();

        BaseResVO promo = promoService.createPromo(promoId, amount, uuid);

        return promo;
    }

    @RequestMapping("publishPromoStock")
    public BaseResVO publishPromoStock() {
        promoService.publishPromoStock();
        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setStatus(0);
        baseResVO.setMsg("发布成功！");
        return baseResVO;
    }
}
