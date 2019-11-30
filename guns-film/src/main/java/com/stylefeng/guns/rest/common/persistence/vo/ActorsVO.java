package com.stylefeng.guns.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ActorsVO implements Serializable {
    private static final long serialVersionUID = 7880909310193116868L;
    private String roleName;
    private String imgAddress;
    private String directorName;
}
