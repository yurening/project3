package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.util.List;

@Data
public class Seats {
    Integer limit;
    String ids;
    List<SingleSeat> single;
    List<CoupleSeat> couple;
}
