package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.dto.CinemaDTO;
import com.stylefeng.guns.rest.vo.BaseResVO;

public interface CinemaTestService {
    BaseResVO getCinemaById(Integer id);

    BaseResVO getFieldsById(Integer id);

    BaseResVO getFieldInfo(Integer cinemaId, Integer fieldId);

}
