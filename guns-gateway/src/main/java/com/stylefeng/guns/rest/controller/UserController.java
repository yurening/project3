package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
<<<<<<< HEAD
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.service.UserService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
=======
import com.stylefeng.guns.rest.vo.BaseResVO;
>>>>>>> 562cfb0f03571f35401f8097b64e1b828f28e563
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController {
<<<<<<< HEAD
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
=======

>>>>>>> 562cfb0f03571f35401f8097b64e1b828f28e563
}
