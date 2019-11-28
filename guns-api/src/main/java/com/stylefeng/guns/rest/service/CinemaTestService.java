package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

public interface CinemaTestService {
    BaseResVO getCinemaById(Integer id);

    BaseResVO getFieldsById(Integer id);
}
