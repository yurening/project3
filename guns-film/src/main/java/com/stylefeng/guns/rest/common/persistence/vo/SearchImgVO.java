package com.stylefeng.guns.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class SearchImgVO implements Serializable {

    private static final long serialVersionUID = 8997675532568429371L;
    private String mainImg;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
}
