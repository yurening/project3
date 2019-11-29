package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.util.List;

@Data
public class CinemaConditionVO {
    List<AreaVO> areaList;
    List<BrandVO> brandList;
    List<HallTypeVO> hallTypeList;
}
