package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

import java.io.IOException;

public interface OrderService {
    BaseResVO buyTickets(Integer fieldId, String soldSeats, String seatsName, Integer uuid) throws IOException;
}
