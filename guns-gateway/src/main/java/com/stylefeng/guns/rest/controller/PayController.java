package com.stylefeng.guns.rest.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.service.AlipayService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class PayController {
    @Reference(interfaceClass = AlipayService.class,check=false)
    AlipayService alipayService;
    @RequestMapping("getPayInfo")
    public BaseResVO getPayInfo(String orderId){
        BaseResVO payInfo = alipayService.getPayInfo(orderId);
        return payInfo;
    }

    @RequestMapping("getPayResult")
    public BaseResVO getPayResult(String orderId,Integer tryNums){
        BaseResVO payResult = alipayService.getPayResult(orderId, tryNums);
        return payResult;
    }
}
