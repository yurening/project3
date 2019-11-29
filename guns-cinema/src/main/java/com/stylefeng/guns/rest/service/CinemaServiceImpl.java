package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaTestService.class)
public class CinemaServiceImpl implements CinemaTestService {
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Override
    public BaseResVO getCinemaById(Integer id) {
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(id);
        BaseResVO baseResVO = new BaseResVO();
        BeanUtils.copyProperties(mtimeFilmT, baseResVO);
        return baseResVO;
    }

    /**
     * 根据id查找影院的片场信息
     * @param id
     * @return
     */
    @Override
    public BaseResVO getFieldsById(Integer id) {
        //首先根据id查找出影院
        MtimeCinemaT cinemaInfo1 = mtimeCinemaTMapper.selectById(id);
        EntityWrapper<MtimeFieldT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("cinema_id",id);
        //再根据影院id查找出具体的放映厅和时间
        List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(entityWrapper);
        //通过放映厅获取电影id的集合
        List<Integer> filmIds = new ArrayList<>();
        for (MtimeFieldT mtimeFieldT : mtimeFieldTS) {
            filmIds.add(mtimeFieldT.getFilmId());
        }
        EntityWrapper<MtimeHallFilmInfoT> entityWrapper1 = new EntityWrapper<>();
        //根据电影id集合获取电影
        entityWrapper1.in("film_id",filmIds);
        List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(entityWrapper1);
        CinemaFields cinemaFields = new CinemaFields();
        //创建实际需要上传的Javabean
        MtimeCinemaT2 cinemaInfo = new MtimeCinemaT2();
        BeanUtils.copyProperties(cinemaInfo1,cinemaInfo);
        cinemaInfo.setCinemaId(cinemaInfo1.getUuid());
        cinemaInfo.setImgUrl(cinemaInfo1.getImgAddress());
        cinemaFields.setCinemaInfo(cinemaInfo);
        //创建实际需要上传的json数据的list
        List<MtimeHallFilmInfoT2> filmList = new ArrayList<>();
        for (MtimeHallFilmInfoT mtimeHallFilmInfoT : mtimeHallFilmInfoTS) {
            MtimeHallFilmInfoT2 mtimeHallFilmInfoT2 = new MtimeHallFilmInfoT2();
            EntityWrapper<MtimeFieldT> entityWrapper2 = new EntityWrapper<>();
            BeanUtils.copyProperties(mtimeHallFilmInfoT,mtimeHallFilmInfoT2);
            entityWrapper2.eq("cinema_id",id);
            entityWrapper2.eq("film_id",mtimeHallFilmInfoT2.getFilmId());
            //根据影院id和电影id选出放映厅id
            List<MtimeFieldT> mtimeFieldTS2 = mtimeFieldTMapper.selectList(entityWrapper2);
            //再封装成实际需要返回的数据
            List<MtimeFieldT2> mtimeFieldT2List = new ArrayList<>();
            for (MtimeFieldT mtimeFieldT : mtimeFieldTS2) {
                MtimeFieldT2 mtimeFieldT2 = new MtimeFieldT2();
                BeanUtils.copyProperties(mtimeFieldT,mtimeFieldT2);
                mtimeFieldT2.setLanguage(mtimeHallFilmInfoT.getFilmLanguage());
                mtimeFieldT2List.add(mtimeFieldT2);
            }
            mtimeHallFilmInfoT2.setFilmFields(mtimeFieldT2List);
            filmList.add(mtimeHallFilmInfoT2);
        }
        //完成封装并返回数据
        cinemaFields.setFilmList(filmList);
        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setData(cinemaFields);
        baseResVO.setStatus(0);
        baseResVO.setImgPre("http://img.meetingshop.cn/");
        return baseResVO;
    }
}