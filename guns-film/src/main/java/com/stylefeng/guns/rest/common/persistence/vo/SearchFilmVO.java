package com.stylefeng.guns.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchFilmVO implements Serializable {
    private static final long serialVersionUID = 5269713409256111063L;
    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;

    //影片类型
    private String info01;
    //影片地区以及时长
    private String info02;
    //影片得上映日期
    private String info03;
    //影片详情以及演员详情
    private Info04VO info04;

    //imgVO
    private SearchImgVO imgVO;
    private String filmId;
}
