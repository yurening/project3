package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.FilmService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.ConditionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("film")
public class FilmController {

    @Reference(interfaceClass = FilmService.class,check=false)
    private FilmService filmService;
    @RequestMapping("film")
    public BaseResVO getFilm(Integer id) {
        BaseResVO filmById = filmService.getFilmById(id);
        return filmById;
    }

    /**
     * searchType是1就是按名称查找，0就是按编号查找
     * 但是现在只是发现了一个按编号查找
     * 所以暂时只写按编号查找得情况
     * @param id
     * @param searchType
     * @return
     */
    @RequestMapping("films/{id}")
    public BaseResVO getFilm(@PathVariable("id") Integer id,Integer searchType){
        //按编号查找
        BaseResVO filmDetailById = filmService.getFilmDetailById(id);
        return filmDetailById;
    }

    /**
     * 按各种条件查找影片
     */
    @RequestMapping("getFilms")
    public BaseResVO getFilms(Integer showType,Integer sortId,
                              Integer catId,Integer sourceId,
                              Integer yearId,Integer nowPage,Integer pageSize){
        BaseResVO filmsByAll = filmService.getFilmsByAll(showType, sortId, catId, sourceId, yearId, nowPage, pageSize);
        return filmsByAll;
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
