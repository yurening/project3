package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.service.OrderService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@ResponseBody
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = OrderService.class,check=false)
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @RequestMapping("buyTickets")
    public BaseResVO buyTickets(Integer fieldId, String soldSeats, String seatsName, HttpServletRequest request) throws IOException {
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(authToken);
        BaseResVO baseResVO = orderService.buyTickets(fieldId,soldSeats,seatsName,userVO.getUuid());
        return baseResVO;
    }

    @RequestMapping("getOrderInfo")
    public BaseResVO getOrderInfo(Integer nowPage,Integer pageSize, HttpServletRequest request){
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = requestHeader.substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(authToken);
        if (nowPage == null){
            nowPage = 1;
        }
        if (pageSize == null){
            pageSize = 5;
        }
        BaseResVO baseResVO = orderService.getOrderInfo(nowPage,pageSize,userVO.getUuid());
        return baseResVO;
    }
}
