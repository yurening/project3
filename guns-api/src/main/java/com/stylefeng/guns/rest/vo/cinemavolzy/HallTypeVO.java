package com.stylefeng.guns.rest.vo.cinemavolzy;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallTypeVO implements Serializable {
    private static final long serialVersionUID = -1299620827231442783L;
    private Integer hallId;
    private String hallName;
    private boolean isActive;
}
