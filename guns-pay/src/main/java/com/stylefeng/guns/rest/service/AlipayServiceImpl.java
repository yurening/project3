package com.stylefeng.guns.rest.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.demo.trade.Main;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBrandDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.vo.BaseResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = AlipayService.class)
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    MoocOrderTMapper moocOrderTMapper;

    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Override
    public BaseResVO getPayInfo(Integer orderId) {
        Main main = new Main();
        //在数据库中查询出订单详情
        EntityWrapper<MoocOrderT> objectEntityWrapper = new EntityWrapper<>();
        MoocOrderT moocOrderT = moocOrderTMapper.selectById(orderId);
        //查询出影院名称
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(moocOrderT.getCinemaId());
        //在mtime_brand_dict_t中查出brand
        MtimeBrandDictT mtimeBrandDictT = mtimeBrandDictTMapper.selectById(mtimeCinemaT.getBrandId());
        //查询出影片名称
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(moocOrderT.getFilmId());
        //进行二维码下载
        String cinemaName = mtimeCinemaT.getCinemaName();
        String showName = mtimeBrandDictT.getShowName();
        String filmName = mtimeFilmT.getFilmName();
        BaseResVO baseResVO = main.test_trade_precreate(moocOrderT, cinemaName, showName, filmName);
        //取出二维码地址，并返回

        return baseResVO;
    }
}