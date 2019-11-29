package com.stylefeng.guns.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController {
<<<<<<< HEAD

=======
    @Reference(interfaceClass = UserTestService.class,check = false)
    UserTestService userTestService;
    @RequestMapping("detail")
    public BaseResVO userDetail(Integer id){
        BaseResVO userById = userTestService.getUserById(id);
        return userById;
    }
>>>>>>> cbf68cf1d4a02f2e20700f619f63ca43c3a79211
}
