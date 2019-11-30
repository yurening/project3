package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.UserService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
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

    @RequestMapping("register")
    public BaseResVO register(UserVO userVO,String username,String password,String mobile){
        userVO.setUserName(username);
        userVO.setUserPhone(mobile);
        Integer status = userService.register(userVO,password);
        if (status == 0) {
            return BaseResVO.fail(0,"用户注册成功！");
        } else {
            return BaseResVO.fail(1,"用户已经注册！");
        }
    }

    @RequestMapping("logout")
    public BaseResVO logout(HttpServletRequest request) {
        final String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        redisTemplate.delete(token);
        return BaseResVO.ok(null);
    }

    /**@获取用户个人信息
     *{
     * 	"data":{
     * 		"address":"12312",
     * 		"begainTime":"2019-11-29 14:34:45",
     * 		"biography":"",
     * 		"birthday":"",
     * 		"email":"8@1.com",
     * 		"headAddress":"",
     * 		"lifeState":"null",
     * 		"nickname":"",
     * 		"phone":"13232323232",
     * 		"sex":0,
     * 		"updateTime":"2019-11-29 14:34:45",
     * 		"username":"1231",
     * 		"uuid":275
     *        },
     * 	"imgPre":"",
     * 	"msg":"",
     * 	"nowPage":"",
     * 	"status":0,
     * 	"totalPage":""
     * }
     */
    @RequestMapping("getUserInfo")
    public BaseResVO getUserInfo(HttpServletRequest request) {
        final String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(token);
        return BaseResVO.ok(userVO);
    }


    /**@校验用户名
     *
     * */
    @RequestMapping("check")
    public BaseResVO check(String username) {
        Integer status = userService.check(username);
        if (status == 0) {
            return BaseResVO.fail(0,"该用户名可以注册！");
        } else {
            return BaseResVO.fail(1,"该用户名已经注册！");
        }
    }


    @RequestMapping("updateUserInfo")
    public BaseResVO updateUserInfo(UserVO userVO, HttpServletRequest request) {
        userService.updateUserInfo(userVO);
        final String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        redisTemplate.opsForValue().set(token, userVO);
        return BaseResVO.ok(userVO);
    }
}



