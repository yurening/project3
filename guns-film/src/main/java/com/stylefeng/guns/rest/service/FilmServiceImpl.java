package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.common.util.Date2String;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.ConditionRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    MtimeBannerTMapper mtimeBannerTMapper;

    @Autowired
    MtimeCatDictTMapper mtimeCatDictTMapper;

    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;

    @Autowired
    MtimeYearDictTMapper mtimeYearDictTMapper;

    @Override
    public BaseResVO getFilmById(Integer id) {
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(id);
        BaseResVO baseResVO = new BaseResVO();
        BeanUtils.copyProperties(mtimeFilmT, baseResVO);
        return baseResVO;
    }

    /**
     * 首页接口
     * @return
     */
    @Override
    public BaseResVO getIndex() {
        BaseResVO<Object> objectBaseResVO = new BaseResVO<>();
        //返回的数据
        FirstPageReturn.DataBean firstPageReturn = new FirstPageReturn.DataBean();
        //在mtime_banner_t表中查询bunners
        List<MtimeBannerT> mtimeBannerTS = mtimeBannerTMapper.selectList(new EntityWrapper<MtimeBannerT>());
        List<FirstPageReturn.DataBean.BannersBean> mtimeBannerReturn = new ArrayList<>();
        for (MtimeBannerT mtimeBannerT : mtimeBannerTS) {
            FirstPageReturn.DataBean.BannersBean bannersBean = new FirstPageReturn.DataBean.BannersBean();
            bannersBean.setBannerId(mtimeBannerT.getUuid().toString());
            bannersBean.setBannerAddress(mtimeBannerT.getBannerAddress());
            bannersBean.setBannerUrl(mtimeBannerT.getBannerUrl());
            mtimeBannerReturn.add(bannersBean);
        }
        firstPageReturn.setBanners(mtimeBannerReturn);
        //在mtime_film_t中根据film_status = 1 查询hotFilms,统计数量
        FirstPageReturn.DataBean.HotFilmsBean hotFilmsBean = new FirstPageReturn.DataBean.HotFilmsBean();
        List<FirstPageReturn.DataBean.HotFilmsBean.FilmInfoBean> filmInfoBeans = new ArrayList<>();
        EntityWrapper<MtimeFilmT> mtimeFilmTEntityWrapper = new EntityWrapper<>();
        mtimeFilmTEntityWrapper.eq("film_status",1);
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(mtimeFilmTEntityWrapper);
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FirstPageReturn.DataBean.HotFilmsBean.FilmInfoBean filmInfoBean = new FirstPageReturn.DataBean.HotFilmsBean.FilmInfoBean();
            filmInfoBean.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfoBean.setFilmType(mtimeFilmT.getFilmType());
            filmInfoBean.setImgAddress(mtimeFilmT.getImgAddress());
            filmInfoBean.setFilmName(mtimeFilmT.getFilmName());
            filmInfoBean.setScore(mtimeFilmT.getFilmScore());
            filmInfoBeans.add(filmInfoBean);
        }
        Integer hotFilmCount = mtimeFilmTMapper.selectCount(mtimeFilmTEntityWrapper);
        hotFilmsBean.setFilmNum(hotFilmCount);
        hotFilmsBean.setFilmInfo(filmInfoBeans);
        firstPageReturn.setHotFilms(hotFilmsBean);
        //在mtime_film_t中根据film_status = 2 查询soonFilms,统计数量
        FirstPageReturn.DataBean.SoonFilmsBean soonFilmsBean = new FirstPageReturn.DataBean.SoonFilmsBean();
        List<FirstPageReturn.DataBean.SoonFilmsBean.FilmInfoBeanX> soonFilms = new ArrayList<>();
        EntityWrapper<MtimeFilmT> mtimeFilmTEntityWrapper1 = new EntityWrapper<>();
        mtimeFilmTEntityWrapper1.eq("film_status",2);
        List<MtimeFilmT> soonFilmsDb = mtimeFilmTMapper.selectList(mtimeFilmTEntityWrapper1);
        for (MtimeFilmT mtimeFilmT : soonFilmsDb) {
            FirstPageReturn.DataBean.SoonFilmsBean.FilmInfoBeanX filmInfoBeanX = new FirstPageReturn.DataBean.SoonFilmsBean.FilmInfoBeanX();
            filmInfoBeanX.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfoBeanX.setFilmType(mtimeFilmT.getFilmType());
            filmInfoBeanX.setFilmName(mtimeFilmT.getFilmName());
            filmInfoBeanX.setExpectNum(mtimeFilmT.getFilmPresalenum());
            filmInfoBeanX.setFilmName(Date2String.transfer(mtimeFilmT.getFilmTime()));
            soonFilms.add(filmInfoBeanX);
        }
        Integer soonSizeDb = mtimeFilmTMapper.selectCount(mtimeFilmTEntityWrapper1);
        soonFilmsBean.setFilmInfo(soonFilms);
        soonFilmsBean.setFilmNum(soonSizeDb);
        //票房排行
        //查询mtime_film_t，film_box_office按照降序取前9位
        EntityWrapper<MtimeFilmT> mtimeFilmTEntityWrapper2 = new EntityWrapper<>();
        mtimeFilmTEntityWrapper2.orderBy("film_box_office",false);
        List<MtimeFilmT> boxRanksDb = mtimeFilmTMapper.selectList(mtimeFilmTEntityWrapper2);
        //定义一个变量boxSize代表票房数量，取前十位既可，即boxSize<=9
        int boxSize = 0;
        List<FirstPageReturn.DataBean.BoxRankingBean> boxRanks = new ArrayList<>();
        for (MtimeFilmT boxRank : boxRanksDb) {
            FirstPageReturn.DataBean.BoxRankingBean boxRankingBean = new FirstPageReturn.DataBean.BoxRankingBean();
           boxRankingBean.setFilmId(boxRank.getUuid().toString());
           boxRankingBean.setImgAddress(boxRank.getImgAddress());
           boxRankingBean.setFilmName(boxRank.getFilmName());
           boxRankingBean.setBoxNum(boxRank.getFilmBoxOffice());
            boxRanks.add(boxRankingBean);
            boxSize++;
            if(boxSize>=9){
                break;
            }
        }
        firstPageReturn.setBoxRanking(boxRanks);
        //最受期待电影,
        //查询mtime_film_t，film_preSaleNum按照降序
        List<FirstPageReturn.DataBean.ExpectRankingBean> expectRanks = new ArrayList<>();
        EntityWrapper<MtimeFilmT> mtimeFilmTEntityWrapper3 = new EntityWrapper<>();
        mtimeFilmTEntityWrapper3.orderBy("film_preSaleNum",false);
        List<MtimeFilmT> expectFilmsDb = mtimeFilmTMapper.selectList(mtimeFilmTEntityWrapper3);
        for (MtimeFilmT mtimeFilmT : expectFilmsDb) {
            FirstPageReturn.DataBean.ExpectRankingBean expectRankingBean = new FirstPageReturn.DataBean.ExpectRankingBean();
            expectRankingBean.setFilmId(mtimeFilmT.getUuid().toString());
            expectRankingBean.setImgAddress(mtimeFilmT.getImgAddress());
            expectRankingBean.setFilmName(mtimeFilmT.getFilmName());
            expectRankingBean.setExpectNum(mtimeFilmT.getFilmPresalenum());
            expectRanks.add(expectRankingBean);
        }
        firstPageReturn.setExpectRanking(expectRanks);
        //top100
        //查询mtime_film_t，film_score按照降序取前100位
        List<FirstPageReturn.DataBean.Top100Bean> top100Beans = new ArrayList<>();
        EntityWrapper<MtimeFilmT> mtimeFilmTEntityWrapper4 = new EntityWrapper<>();
        mtimeFilmTEntityWrapper4.orderBy("film_score",false);
        int topSize = 0;
        List<MtimeFilmT> top100Db = mtimeFilmTMapper.selectList(mtimeFilmTEntityWrapper4);
        for (MtimeFilmT mtimeFilmT : top100Db) {
            FirstPageReturn.DataBean.Top100Bean top100Bean = new FirstPageReturn.DataBean.Top100Bean();
            top100Bean.setFilmId(mtimeFilmT.getUuid().toString());
            top100Bean.setImgAddress(mtimeFilmT.getImgAddress());
            top100Bean.setFilmName(mtimeFilmT.getFilmName());
            top100Bean.setScore(mtimeFilmT.getFilmScore());
            top100Beans.add(top100Bean);
            topSize++;
            if(topSize>=100){
                break;
            }
        }
        firstPageReturn.setTop100(top100Beans);
         objectBaseResVO.setData(firstPageReturn);
         objectBaseResVO.setStatus(0);
        return objectBaseResVO;
    }

    /**
     * 影片条件列表查询接口
     * 从mtime_cat_dict_t、mtime_source_dict_t、mtime_year_dict_t表中查出数据
     * 当id等于前端传来的id时，将active设为true，其余为false
     * @param conditionRequest
     * @return
     */
    @Override
    public BaseResVO getConditionList(ConditionRequest conditionRequest) {
        BaseResVO<Object> objectBaseResVO = new BaseResVO<>();
        ConditionReturn.DataBean dataBean = new ConditionReturn.DataBean();
        //从mtime_cat_dict_t查询出所有数据
        EntityWrapper<MtimeCatDictT> mtimeCatDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeCatDictT> cats = mtimeCatDictTMapper.selectList(mtimeCatDictTEntityWrapper);
        List<ConditionReturn.DataBean.CatInfoBean> catInfoBeans = new ArrayList<>();
        for (MtimeCatDictT cat : cats) {
            ConditionReturn.DataBean.CatInfoBean catInfoBean = new ConditionReturn.DataBean.CatInfoBean();
            catInfoBean.setCatId(cat.getUuid().toString());
            catInfoBean.setCatName(cat.getShowName());
            if(conditionRequest.getCatId()==cat.getUuid()){
                catInfoBean.setActive(true);
            }else {
                catInfoBean.setActive(false);
            }
            catInfoBeans.add(catInfoBean);
        }
        dataBean.setCatInfo(catInfoBeans);
        //从mtime_source_dict_t查询出所有数据
        List<ConditionReturn.DataBean.SourceInfoBean> sourceInfoBeans = new ArrayList<>();
        EntityWrapper<MtimeSourceDictT> mtimeSourceDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeSourceDictT> mtimeSourceDictTS = mtimeSourceDictTMapper.selectList(mtimeSourceDictTEntityWrapper);
        for (MtimeSourceDictT mtimeSourceDictT : mtimeSourceDictTS) {
            ConditionReturn.DataBean.SourceInfoBean sourceInfoBean = new ConditionReturn.DataBean.SourceInfoBean();
            sourceInfoBean.setSourceId(mtimeSourceDictT.getUuid().toString());
            sourceInfoBean.setSourceName(mtimeSourceDictT.getShowName());
            if(conditionRequest.getSourceId() == mtimeSourceDictT.getUuid()){
                sourceInfoBean.setActive(true);
            }else {
                sourceInfoBean.setActive(false);
            }
            sourceInfoBeans.add(sourceInfoBean);
        }
        //从mtime_year_dict_t查询出所有数据
        List<ConditionReturn.DataBean.YearInfoBean> yearInfoBeans = new ArrayList<>();
        EntityWrapper<MtimeYearDictT> mtimeYearDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeYearDictT> mtimeYearDictTS = mtimeYearDictTMapper.selectList(mtimeYearDictTEntityWrapper);
        for (MtimeYearDictT mtimeYearDictT : mtimeYearDictTS) {
            ConditionReturn.DataBean.YearInfoBean yearInfoBean = new ConditionReturn.DataBean.YearInfoBean();
            yearInfoBean.setYearId(mtimeYearDictT.getUuid().toString());
            yearInfoBean.setYearName(mtimeYearDictT.getShowName());
            if(conditionRequest.getYearId() == mtimeYearDictT.getUuid()){
                yearInfoBean.setActive(true);
            }else {
                yearInfoBean.setActive(false);
            }
            yearInfoBeans.add(yearInfoBean);
        }
        dataBean.setYearInfo(yearInfoBeans);
        objectBaseResVO.setData(dataBean);
        objectBaseResVO.setStatus(0);
        return objectBaseResVO;
    }

}
