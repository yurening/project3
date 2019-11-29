package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.common.persistence.vo.Actors;
import com.stylefeng.guns.rest.common.persistence.vo.Info04VO;
import com.stylefeng.guns.rest.common.persistence.vo.SearchFilmVO;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;
    @Autowired
    MtimeFilmInfoTMapper mtimeFilmInfoTMapper;
    @Autowired
    MtimeCatDictTMapper mtimeCatDictTMapper;
    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;
    @Autowired
    MtimeActorTMapper mtimeActorTMapper;
    @Autowired
    MtimeFilmActorTMapper mtimeFilmActorTMapper;

    @Override
    public BaseResVO getFilmDetailById(Integer id) {
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(id);
        SearchFilmVO searchFilmVO = new SearchFilmVO();
        searchFilmVO.setFilmName(mtimeFilmT.getFilmName());
        searchFilmVO.setImgAddress(mtimeFilmT.getImgAddress());
        searchFilmVO.setScore(mtimeFilmT.getFilmScore());

        //获取影片英文名
        EntityWrapper<MtimeFilmInfoT> filmInfoTEntityWrapper = new EntityWrapper<>();
        filmInfoTEntityWrapper.eq("film_id",mtimeFilmT.getUuid());
        List<MtimeFilmInfoT> filmInfoTS = mtimeFilmInfoTMapper.selectList(filmInfoTEntityWrapper);
        MtimeFilmInfoT mtimeFilmInfoT = filmInfoTS.get(0);
        searchFilmVO.setFilmEnName(mtimeFilmInfoT.getFilmEnName());

        //票房计算（以万为单位）换算成以亿为单位
        Integer filmBoxOffice = mtimeFilmT.getFilmBoxOffice();
        //判断：是否超过一亿
        if (filmBoxOffice>10000){
            //超过了一亿
            double realFilmBox= filmBoxOffice / 10000.0;
            String totalBox = String.valueOf(realFilmBox);
            searchFilmVO.setTotalBox(totalBox+"亿");
        }else{
            String totalBox = String.valueOf(filmBoxOffice);
            searchFilmVO.setTotalBox(totalBox+"万");
        }

        //获取影片分类得字符串
        String filmCats = mtimeFilmT.getFilmCats();
        String[] cats = filmCats.split("#");
        StringBuilder stringBuilder = new StringBuilder();
        for (String cat : cats) {
            if ("".equals(cat)){
                continue;
            }
            int catId = Integer.parseInt(cat);
            MtimeCatDictT mtimeCatDictT = mtimeCatDictTMapper.selectById(catId);
            String showName = mtimeCatDictT.getShowName();
            stringBuilder.append(showName+",");
        }
        String filmCat = stringBuilder.toString();
        String info01 = filmCat.substring(0,filmCat.length()-1);
        searchFilmVO.setInfo01(info01);

        //获取影片上映地区以及影片时长
        Integer filmSource = mtimeFilmT.getFilmSource();//片源
        Integer filmArea = mtimeFilmT.getFilmArea();//区域
        MtimeSourceDictT sourceFilm = mtimeSourceDictTMapper.selectById(filmSource);
        String sourceMovie = sourceFilm.getShowName();
        MtimeSourceDictT areaFilm = mtimeSourceDictTMapper.selectById(filmArea);
        String sourceArea = areaFilm.getShowName();
        Integer uuid = mtimeFilmT.getUuid();
        Integer filmLength = mtimeFilmInfoT.getFilmLength();//时长
        String filmTime = filmLength.toString();
        String info02 = sourceArea+","+sourceMovie+"/"+filmLength+"分钟";
        searchFilmVO.setInfo02(info02);

        //获取上映时间（2018-06-29大陆上映）
        Date filmTime1 = mtimeFilmT.getFilmTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateFilm = format.format(filmTime1);
        searchFilmVO.setInfo03(dateFilm+"大陆上映");

        //获取影片详细信息Info04
        Info04VO info04 = new Info04VO();
        String biography = mtimeFilmInfoT.getBiography();
        info04.setBiography(biography);
        Integer directorId = mtimeFilmInfoT.getDirectorId();//导演编号
        MtimeActorT director = mtimeActorTMapper.selectById(directorId);
        Actors actors = new Actors();
        actors.setDirector(director);
        //获取主演得演员们
        //拿到演员编号
        EntityWrapper<MtimeFilmActorT> mtimeFilmActorEntity = new EntityWrapper<>();
        mtimeFilmActorEntity.eq("film_id",mtimeFilmInfoT.getFilmId());
        List<MtimeFilmActorT> mtimeFilmActorTS = mtimeFilmActorTMapper.selectList(mtimeFilmActorEntity);
        //按编号取演员信息
        List<MtimeActorT> actorList = new ArrayList<>();
        for (MtimeFilmActorT mtimeFilmActorT : mtimeFilmActorTS) {
            EntityWrapper<MtimeActorT> mtimeActorEntity = new EntityWrapper<>();
            mtimeActorEntity.eq("UUID",mtimeFilmActorT.getActorId());
            List<MtimeActorT> mtimeActorTS = mtimeActorTMapper.selectList(mtimeActorEntity);
            MtimeActorT mtimeActorT = mtimeActorTS.get(0);
            actorList.add(mtimeActorT);
        }
        actors.setActors(actorList);
        info04.setActors(actors);
        searchFilmVO.setInfo04(info04);

        String s = String.valueOf(id);
        searchFilmVO.setFilmId(s);

        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setData(searchFilmVO);
        baseResVO.setStatus(0);
        return baseResVO;
    }

    @Override
    public BaseResVO getFilmsByAll(Integer showType, Integer sortId,
                                   Integer catId, Integer sourceId,
                                   Integer yearId, Integer nowPage, Integer pageSize) {
        EntityWrapper<MtimeFilmT> mtimeFilmTEntityWrapper = new EntityWrapper<>();
        String sortIds = String.valueOf(sortId);
        mtimeFilmTEntityWrapper.eq("film_status",showType).like("film_cats",sortIds).eq("film_source",sourceId);
        mtimeFilmTEntityWrapper.eq("film_date",yearId);
        if (sortId==1){
            mtimeFilmTEntityWrapper.orderBy("film_box_office",false);
        }
        if (sortId==2){
            mtimeFilmTEntityWrapper.orderBy("film_time",false);
        }
        if (sortId==3){
            mtimeFilmTEntityWrapper.orderBy("film_score",false);
        }
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(mtimeFilmTEntityWrapper);

        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setStatus(0);
        baseResVO.setData(mtimeFilmTS);
        return baseResVO;
    }
}
