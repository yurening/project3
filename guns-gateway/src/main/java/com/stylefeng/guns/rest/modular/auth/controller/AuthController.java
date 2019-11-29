package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.service.UserService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "${jwt.auth-path}")
    public BaseResVO createAuthenticationToken(AuthRequest authRequest) {

//        boolean validate = reqValidator.validate(authRequest);
        UserVO userVO = userService.login(authRequest.getUserName(), authRequest.getPassword());
        if (userVO != null) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            redisTemplate.opsForValue().set(token, userVO);
            redisTemplate.expire(token, 7, TimeUnit.DAYS);
            return BaseResVO.ok(new AuthResponse(token, randomKey));
        } else {
            return BaseResVO.fail(1, "用户名或密码错误");
        }
    }
}
