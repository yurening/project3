package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

import java.util.HashMap;

public interface PromoService {

    //获取活动信息
    BaseResVO getPromo(Integer brandId,String hallType,
                       Integer areaId,Integer pageSize,Integer nowPage);

    //添加秒杀订单
    Boolean createPromo(Integer promoId ,Integer amount,Integer userId, String stockLogId);
    void publishPromoStock();

    String initPromoStockLog(Integer promoId, Integer amount);

    void createPromoOrder(HashMap hashMap) throws Exception;

    Integer getStatusByStockLogId(String stockLogId);
}
