package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.FilmTestService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("film")
public class FilmController {
    @Reference(interfaceClass = FilmTestService.class)
    private FilmTestService filmTestService;
    @RequestMapping("film")
    public BaseResVO getFilm(Integer id){
        BaseResVO filmById = filmTestService.getFilmById(id);
        return filmById;
    }

}
