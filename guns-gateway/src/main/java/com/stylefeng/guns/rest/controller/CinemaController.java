package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.CinemaTestService;
import com.stylefeng.guns.rest.service.cinemalzy.IMtimeCinemaTService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaBaseVO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaConditionVO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaInfoVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@ResponseBody
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaTestService.class,check = false)
    private CinemaTestService cinemaTestService;
    @RequestMapping("cinema")
    public BaseResVO cinema(Integer id){
        BaseResVO cinemaById = cinemaTestService.getCinemaById(id);
        return cinemaById;
    }

    @RequestMapping("getFields")
    public BaseResVO getFields(Integer cinemaId){
        BaseResVO fields = cinemaTestService.getFieldsById(cinemaId);
        return fields;
    }

    @Reference(interfaceClass = IMtimeCinemaTService.class,check = false)
    IMtimeCinemaTService cinemaTService;

    @RequestMapping("getCinemas")
    public CinemaBaseVO<List<CinemaInfoVO>> getCinemas(Integer brandId, Integer hallType, Integer districtId, Integer pageSize, Integer nowPage){
        return cinemaTService.getCinemasItem(brandId,hallType,districtId,pageSize,nowPage);
    }

    @RequestMapping("getCondition")
    public CinemaBaseVO<CinemaConditionVO> getCondition(Integer brandId, Integer hallType, Integer areaId){
        return cinemaTService.getCinemaCondition(brandId,areaId,hallType);
    }
}
