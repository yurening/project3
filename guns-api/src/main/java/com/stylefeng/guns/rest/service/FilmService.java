package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.ConditionRequest;

public interface FilmService {
    BaseResVO getFilmById(Integer id);

    BaseResVO getIndex();

    BaseResVO getConditionList(ConditionRequest conditionRequest);
}
