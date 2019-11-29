package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaInfoVO implements Serializable {
    private static final long serialVersionUID = -2152630086484148640L;
    /**
     * 主键编号
     */
    private Integer uuid;
    /**
     * 影院名称
     */
    private String cinemaName;
    /**
     * 影院电话
     */
    private String cinemaPhone;
    /**
     * 品牌编号
     */
    private Integer brandId;
    /**
     * 地域编号
     */
    private Integer areaId;
    /**
     * 包含的影厅类型,以#作为分割
     */
    private String hallIds;
    /**
     * 影院图片地址
     */
    private String imgAddress;
    /**
     * 影院地址
     */
    private String cinemaAddress;
    /**
     * 最低票价
     */
    private Integer minimumPrice;
}
