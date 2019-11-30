package com.stylefeng.guns.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @RequestMapping("login")
    public Map login() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 700);
        return map;
        }
    }

