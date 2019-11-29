package com.stylefeng.guns.rest.service.impllzy;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeAreaDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBrandDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeAreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallDictT;
import com.stylefeng.guns.rest.service.cinemalzy.IMtimeCinemaTService;
import com.stylefeng.guns.rest.vo.cinemavolzy.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 影院信息表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-11-28
 */
@Component
@Service(interfaceClass = IMtimeCinemaTService.class)
public class MtimeCinemaTServiceImpl implements IMtimeCinemaTService {
    @Autowired
    MtimeCinemaTMapper cinemaTMapper;
    @Autowired
    MtimeAreaDictTMapper areaMapper;
    @Autowired
    MtimeBrandDictTMapper brandMapper;
    @Autowired
    MtimeHallDictTMapper hallMapper;

    /**
     * 查找影院
     * @param brandId
     * @param hallType
     * @param districtId
     * @param pageSize
     * @param nowPage
     * @return
     */
    @Override
    public CinemaBaseVO<List<CinemaInfoVO>> getCinemasItem(Integer brandId, Integer hallType, Integer districtId, Integer pageSize, Integer nowPage) {
        EntityWrapper<MtimeCinemaT> cinemWrapper = new EntityWrapper<>();
        if(brandId!=null){
            cinemWrapper.eq("brand_id",brandId);
        }
        if(hallType!=null){
            cinemWrapper.eq("hall_ids",hallType);
        }
        if(districtId!=null){
            cinemWrapper.eq("area_id",districtId);
        }
        if(nowPage!=null&&pageSize!=null){
        PageHelper.startPage(nowPage,pageSize);}
        List<MtimeCinemaT> mtimeCinemaTS = cinemaTMapper.selectList(cinemWrapper);
        PageInfo<MtimeCinemaT> pageInfo = new PageInfo<>(mtimeCinemaTS);

        /*IPage<MtimeCinemaT> pageInfo = cinemaTMapper.selectListByPage(page, cinemWrapper);*/

        CinemaBaseVO<List<CinemaInfoVO>> result = new CinemaBaseVO<>();

        if(pageInfo==null||pageInfo.getSize()==0){
            result.setStatus(1);
            result.setMsg("查询错误");
            return result;
        }
        long pages = pageInfo.getPages();
        List<CinemaInfoVO> resultCinemas = convert2CinemaInfoVO(mtimeCinemaTS);
        result.setStatus(0);
        result.setNowPage(nowPage);
        result.setTotalPage((int)pages);
        result.setData(resultCinemas);
        result.setMsg("");
        return result;
    }

    /**
     * 查询结果转换VO
     * @param mtimeCinemaTS
     * @return
     */
    private List<CinemaInfoVO> convert2CinemaInfoVO(List<MtimeCinemaT> mtimeCinemaTS) {
        ArrayList<CinemaInfoVO> cinemaInfoVOS = new ArrayList<>();
        if(mtimeCinemaTS==null||mtimeCinemaTS.size()==0){
            return cinemaInfoVOS;
        }
        for(MtimeCinemaT x:mtimeCinemaTS){
            CinemaInfoVO target = new CinemaInfoVO();
            BeanUtils.copyProperties(x, target);
            cinemaInfoVOS.add(target);
        }
        return cinemaInfoVOS;
    }

    /**
     * 查询影院条件
     * @param brandId
     * @param areaId
     * @param hallType
     * @return
     */
    @Override
    public CinemaBaseVO<CinemaConditionVO> getCinemaCondition(Integer brandId, Integer areaId, Integer hallType) {
        CinemaBaseVO<CinemaConditionVO> result = new CinemaBaseVO<>();
        CinemaConditionVO condition = new CinemaConditionVO();

        EntityWrapper<MtimeBrandDictT> brandWrap = new EntityWrapper<>();
        if(brandId!=null){
            brandWrap.eq("UUID",brandId);
        }
        List<MtimeBrandDictT> brandList = brandMapper.selectList(brandWrap);
        List<BrandVO> brandResults = convert2BrandVO(brandList);
        condition.setBrandList(brandResults);

        EntityWrapper<MtimeAreaDictT> areaWrap = new EntityWrapper<>();
        if(areaId!=null){
            areaWrap.eq("UUID",areaId);
        }
        List<MtimeAreaDictT> areaList = areaMapper.selectList(areaWrap);
        List<AreaVO> areaResults = convert2AreaVO(areaList);
        condition.setAreaList(areaResults);

        EntityWrapper<MtimeHallDictT> hallWrap = new EntityWrapper<>();
        if(hallType!=null){
            hallWrap.eq("UUID",hallType);
        }
        List<MtimeHallDictT> hallList = hallMapper.selectList(hallWrap);
        List<HallTypeVO> hallResults = convert2HallVO(hallList);
        condition.setHallTypeList(hallResults);

        result.setStatus(0);
        result.setData(condition);
        return result;
    }

    private List<HallTypeVO> convert2HallVO(List<MtimeHallDictT> hallList) {
        if(hallList==null){return null;}
        ArrayList<HallTypeVO> hallVOS = new ArrayList<>();
        for(MtimeHallDictT x:hallList){
            HallTypeVO hallVO = new HallTypeVO();
            hallVO.setHallId(x.getUuid());
            hallVO.setHallName(x.getShowName());
            hallVO.setActive(false);
            hallVOS.add(hallVO);
        }
        return hallVOS;
    }

    private List<AreaVO> convert2AreaVO(List<MtimeAreaDictT> areaList) {
        if(areaList==null){return null;}
        ArrayList<AreaVO> areaVOS = new ArrayList<>();
        for(MtimeAreaDictT x:areaList){
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(x.getUuid());
            areaVO.setAreaName(x.getShowName());
            areaVO.setActive(false);
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }

    private List<BrandVO> convert2BrandVO(List<MtimeBrandDictT> brandList) {
        if(brandList==null){return null;}
        ArrayList<BrandVO> brandVOS = new ArrayList<>();
        for(MtimeBrandDictT x:brandList){
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(x.getUuid());
            brandVO.setBrandName(x.getShowName());
            brandVO.setActive(false);
            brandVOS.add(brandVO);
        }
        return brandVOS;
    }
}
