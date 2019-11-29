package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConditionRequest implements Serializable {
    private static final long serialVersionUID = -2691981986548786510L;
    private Integer catId;
    private Integer sourceId;
    private Integer yearId;
}
