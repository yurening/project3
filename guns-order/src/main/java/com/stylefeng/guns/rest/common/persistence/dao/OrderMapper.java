package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {

    int insert(MoocOrderT moocOrderT);

    @Select({
            "select o.uuid as orderId,f2.film_name as filmName,f1.begin_time as fieldTime" +
            ",c.cinema_name as cinemaName,o.seats_name as seatsName,o.order_time as orderTimestamp," +
            "o.order_price as orderPrice,o.order_status as orderStatus" +
            " from mooc_order_t o left join mtime_field_t f1 on o.field_id = f1.uuid" +
            " left join mtime_hall_film_info_t f2 on o.film_id = f2.film_id" +
            " left join mtime_cinema_t c on c.UUID = o.cinema_id " +
            "where o.order_user = #{orderUser} order by orderTimestamp desc"
    })
    List<OrderVo> orderInfo(@Param("orderUser") Integer orderUser);
}
