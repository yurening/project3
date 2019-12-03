package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromoForCinemaVO implements Serializable {
    private static final long serialVersionUID = 4068716002722862417L;
    private String cinemaAddress;
    private String cinemaName;
    private Integer cinemaId;
    private String description;
    private Date endTime;
    private String imgAddress;
    private BigDecimal price;
    private Date startTime;
    private Integer status;
    private Integer stock;
    private Integer uuid;
}
