package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.BaseResVO;

public interface AlipayService {
    public BaseResVO getPayInfo(Integer orderId);
}
