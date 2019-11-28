package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.UserTestService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserTestService.class,check = false)
    UserTestService userTestService;
    @RequestMapping("detail")
    public BaseResVO userDetail(Integer id){
        BaseResVO userById = userTestService.getUserById(id);
        return userById;
    }
}
