package com.stylefeng.guns.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IllegalController {
    @RequestMapping("/123")
    public String unknownPage() {
        System.out.println(123);
        return "/WEB-INF/unknown.html";
    }
}
