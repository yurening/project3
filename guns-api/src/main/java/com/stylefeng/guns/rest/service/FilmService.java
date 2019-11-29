package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

public interface FilmService {
    //影片详情：按编号查找
    BaseResVO getFilmDetailById(Integer id);
    //影片查找：各种条件
    BaseResVO getFilmsByAll(Integer showType,Integer sortId,
                            Integer catId,Integer sourceId,
                            Integer yearId,Integer nowPage,Integer pageSize);

}
