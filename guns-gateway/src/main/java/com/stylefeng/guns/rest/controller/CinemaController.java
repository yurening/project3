package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.CinemaTestService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@ResponseBody
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaTestService.class,check=false)
    private CinemaTestService cinemaTestService;
    @RequestMapping("cinema")
    public BaseResVO cinema(Integer id){
        BaseResVO cinemaById = cinemaTestService.getCinemaById(id);
        return cinemaById;
    }
}
