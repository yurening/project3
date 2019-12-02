package com.stylefeng.guns.rest.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderVo implements Serializable {


    private static final long serialVersionUID = -4327348079396369855L;

    String orderId;

    String filmName;

    String fieldTime;

    String orderPrice;

    String orderTimestamp;

    String cinemaName;

    String seatsName;

    String orderStatus;

}
