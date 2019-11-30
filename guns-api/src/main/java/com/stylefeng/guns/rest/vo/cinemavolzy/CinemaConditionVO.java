package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaConditionVO implements Serializable {
    private static final long serialVersionUID = 520061771637796486L;
    List<AreaVO> areaList;
    List<BrandVO> brandList;
    List<HallTypeVO> halltypeList;
}
