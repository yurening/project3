package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.UserService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController {

    @Reference(interfaceClass = UserService.class,check = false)
    private UserService userService;

    @RequestMapping("register")
    public BaseResVO register(UserVO userVO,String password){
        Integer status = userService.register(userVO,password);
        switch (status) {
            case 0:return BaseResVO.fail(0,"用户注册成功！");
            case 1:return BaseResVO.fail(1,"用户已经注册！");
            default:return BaseResVO.fail(999,"服务器异常！");
        }
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
    public BaseResVO getUserInfo(String username, HttpServletRequest request) {
        final String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(token);
        return BaseResVO.ok(userVO);
    }

    @RequestMapping("check")
    public BaseResVO check(String username) {
        Integer status = userService.check(username);
        switch (status) {
            case 0:
                return BaseResVO.fail(0, "用户不存在！");
            case 1:
                return BaseResVO.fail(1, "用户已经注册！");
            default:
                return BaseResVO.fail(999, "服务器异常！");
        }
    }
}