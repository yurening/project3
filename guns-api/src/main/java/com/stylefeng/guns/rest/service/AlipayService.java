package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

public interface AlipayService {
    //获得支付二维码
    public BaseResVO getPayInfo(String orderId);
    //查询订单状态
    public BaseResVO getPayResult(String orderId,Integer tryNums);
}
