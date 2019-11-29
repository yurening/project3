package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.FilmService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.ConditionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("film")
public class FilmController {
    @Reference(interfaceClass = FilmService.class,check=false)
    private FilmService filmService;
    @RequestMapping("film")
    public BaseResVO getFilm(Integer id){
        BaseResVO filmById = filmService.getFilmById(id);
        return filmById;
    }

    @RequestMapping("getIndex")
    public BaseResVO getIndex(){
        return filmService.getIndex();

    }

    @RequestMapping("getConditionList")
    public BaseResVO getCondiotionList(ConditionRequest conditionRequest){
        return filmService.getConditionList(conditionRequest);

    }
}
