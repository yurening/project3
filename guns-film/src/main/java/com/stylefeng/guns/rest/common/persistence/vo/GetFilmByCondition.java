package com.stylefeng.guns.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于影片搜索的展示
 */
@Data
public class GetFilmByCondition implements Serializable {
    private static final long serialVersionUID = 176473827967534383L;
    private String filmId;
    private String filmType;
    private String imgAddress;
    private String filmScore;
    private String filmName;
}
