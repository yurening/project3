package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = FilmTestService.class)
public class FilmTestServiceImpl implements FilmTestService {
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;
    @Override
    public BaseResVO getFilmById(Integer id) {
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(id);
        BaseResVO baseResVO = new BaseResVO();
        BeanUtils.copyProperties(mtimeFilmT, baseResVO);
        return baseResVO;
    }
}
