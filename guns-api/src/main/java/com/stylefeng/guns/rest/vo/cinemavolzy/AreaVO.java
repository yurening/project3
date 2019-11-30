package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaVO implements Serializable {
    private static final long serialVersionUID = -4670151903619138534L;
    private Integer areaId;
    private String areaName;
    private boolean isActive;
}
