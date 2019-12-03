package com.stylefeng.guns.rest.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaDTO implements Serializable {
    private static final long serialVersionUID = -507577485196201410L;
    private String cinemaAddress;
    private Integer cinemaId;
    private String cinemaName;
    private String imgAddress;
}
