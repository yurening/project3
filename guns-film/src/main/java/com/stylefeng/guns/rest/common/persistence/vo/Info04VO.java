package com.stylefeng.guns.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Map;

@Data
public class Info04VO implements Serializable {
    private static final long serialVersionUID = -2286035660037187747L;
    private String biopgraphy;
    private Actors actors;
    private Map<String,String> imgVO;
    private String filmId;
}
