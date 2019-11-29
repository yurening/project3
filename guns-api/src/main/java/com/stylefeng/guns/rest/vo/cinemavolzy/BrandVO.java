package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVO implements Serializable {
    private static final long serialVersionUID = 8727128876262445249L;
    private Integer brandId;
    private String brandName;
    private boolean isActive;
}
