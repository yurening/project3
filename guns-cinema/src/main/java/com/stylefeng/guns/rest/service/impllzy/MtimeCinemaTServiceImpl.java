package com.stylefeng.guns.rest.service.impllzy;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
import com.stylefeng.guns.rest.dto.CinemaDTO;
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
     * @param areaId
     * @param pageSize
     * @param nowPage
     * @return
     */
    @Override
    public CinemaBaseVO<List<CinemaInfoVO>> getCinemasItem(Integer brandId, Integer hallType,Integer halltypeId, Integer areaId, Integer pageSize, Integer nowPage) {
        EntityWrapper<MtimeCinemaT> cinemWrapper = new EntityWrapper<>();
        if(brandId!=null&&brandId!=99){
            cinemWrapper.eq("brand_id",brandId);
        }
        if(hallType!=null&&hallType!=99){
            cinemWrapper.like("hall_ids",hallType.toString());
        }
        if(halltypeId!=null&&halltypeId!=99){
            cinemWrapper.like("hall_ids",halltypeId.toString());
        }
        if(areaId!=null&&areaId!=99){
            cinemWrapper.eq("area_id",areaId);
        }
        if(nowPage!=null&&pageSize!=null){
        PageHelper.startPage(nowPage,pageSize);}
        List<MtimeCinemaT> mtimeCinemaTS = cinemaTMapper.selectList(cinemWrapper);
        PageInfo<MtimeCinemaT> pageInfo = new PageInfo<>(mtimeCinemaTS);

        /*IPage<MtimeCinemaT> pageInfo = cinemaTMapper.selectListByPage(page, cinemWrapper);*/

        CinemaBaseVO<List<CinemaInfoVO>> result = new CinemaBaseVO<>();

        /*if(pageInfo==null||pageInfo.getSize()==0){
            result.setStatus(1);
            result.setMsg("查询错误");
            return result;
        }*/
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
        /*if(brandId!=null&&brandId!=99){
            brandWrap.eq("UUID",brandId);
        }*/
        List<MtimeBrandDictT> brandList = brandMapper.selectList(brandWrap);
        List<BrandVO> brandResults = convert2BrandVO(brandList);
        if(brandId!=null&&brandId!=99) {
            for (BrandVO x : brandResults) {
                if (x.getBrandId()==brandId){x.setActive(true);}
            }
        }
        condition.setBrandList(brandResults);

        EntityWrapper<MtimeAreaDictT> areaWrap = new EntityWrapper<>();
        /*if(areaId!=null&&areaId!=99){
            areaWrap.eq("UUID",areaId);
        }*/
        List<MtimeAreaDictT> areaList = areaMapper.selectList(areaWrap);
        List<AreaVO> areaResults = convert2AreaVO(areaList);
        if(areaId!=null&&areaId!=99) {
            for (AreaVO x : areaResults) {
                if (x.getAreaId()==areaId){x.setActive(true);}
            }
        }
        condition.setAreaList(areaResults);

        EntityWrapper<MtimeHallDictT> hallWrap = new EntityWrapper<>();
        /*if(hallType!=null&&hallType!=99){
            hallWrap.eq("UUID",hallType);
        }*/
        List<MtimeHallDictT> hallList = hallMapper.selectList(hallWrap);
        List<HallTypeVO> hallResults = convert2HallVO(hallList);
        if(hallType!=null&&hallType!=99) {
            for (HallTypeVO x : hallResults) {
                if (x.getHalltypeId()==hallType){x.setActive(true);}
            }
        }
        condition.setHalltypeList(hallResults);

        result.setStatus(0);
        result.setData(condition);
        return result;
    }

    /**
     * yzn:获取秒杀信息
     * @param brandId
     * @param areaId
     * @param hallType
     * @return
     */
    @Override
    public List<CinemaDTO> getCinemaForPromo(Integer brandId, Integer areaId,String hallType,Integer pageSize, Integer nowPage) {
        EntityWrapper<MtimeCinemaT> cinemaTEntityWrapper = new EntityWrapper<>();
        Page page = new Page(nowPage,pageSize);
        if (brandId!=99){
            cinemaTEntityWrapper.eq("brand_id", brandId);
        }
        if (areaId!=99){
            cinemaTEntityWrapper.eq("area_id",areaId);
        }
        if (!"99".equals(hallType)){
            cinemaTEntityWrapper.like("hall_ids",hallType);
        }
        List<CinemaDTO> list = new ArrayList<>();
        List<MtimeCinemaT> mtimeCinemaTS = cinemaTMapper.selectPage(page,cinemaTEntityWrapper);
        for (MtimeCinemaT cinemaT : mtimeCinemaTS) {
            CinemaDTO cinemaDTO = new CinemaDTO();
            cinemaDTO.setCinemaAddress(cinemaT.getCinemaAddress());
            cinemaDTO.setCinemaId(cinemaT.getUuid());
            cinemaDTO.setCinemaName(cinemaT.getCinemaName());
            cinemaDTO.setImgAddress(cinemaT.getImgAddress());
            list.add(cinemaDTO);
        }

        return list;
    }

    private List<HallTypeVO> convert2HallVO(List<MtimeHallDictT> hallList) {
        if(hallList==null){return null;}
        ArrayList<HallTypeVO> hallVOS = new ArrayList<>();
        for(MtimeHallDictT x:hallList){
            HallTypeVO hallVO = new HallTypeVO();
            hallVO.setHalltypeId(x.getUuid());
            hallVO.setHalltypeName(x.getShowName());
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
