package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.service.UserService;
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
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class, check = false)
    UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("updateUserInfo")
    public BaseResVO updateUserInfo(UserVO userVO, HttpServletRequest request) {
        userService.updateUserInfo(userVO);
        final String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        redisTemplate.opsForValue().set(token, userVO);
        return BaseResVO.ok(userVO);
    }
}
