package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

public interface PromoService {

    //获取活动信息
    BaseResVO getPromo(Integer brandId,String hallType,
                       Integer areaId,Integer pageSize,Integer nowPage);

    //添加秒杀订单
    BaseResVO createPromo(Integer promoId ,Integer amount,Integer userId);
}
