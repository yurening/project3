package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.OrderVo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    MoocOrderTMapper moocOrderTMapper;

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Autowired
    OrderMapper orderMapper;

/*    @Value("classpath:static/seats/123214.json")
    private Resource areaRes;*/

    @Override
    public BaseResVO buyTickets(Integer fieldId, String soldSeats, String seatsName, Integer uuid) throws IOException {
        BaseResVO baseResVO = new BaseResVO();
        OrderVo orderVo = new OrderVo();
        EntityWrapper<MoocOrderT> orderWrapper = new EntityWrapper<>();
        orderWrapper.eq("field_id",fieldId);
        List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(orderWrapper);
        String seatsIds = new String();
        for (MoocOrderT moocOrderT : moocOrderTS) {
            seatsIds = seatsIds + moocOrderT.getSeatsIds() + ",";
        }
        String[] split2 = seatsIds.split(",");
        String[] split = soldSeats.split(",");
        EntityWrapper<MtimeFieldT> fieldWrapper = new EntityWrapper<>();
        fieldWrapper.eq("UUID",fieldId);
        List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(fieldWrapper);
        MtimeFieldT mtimeFieldT = mtimeFieldTS.get(0);
        boolean isNotExist = true;
        Integer hallId = mtimeFieldT.getHallId();
        EntityWrapper<MtimeHallDictT> hallWrapper = new EntityWrapper<>();
        hallWrapper.eq("UUID",hallId);
        List<MtimeHallDictT> mtimeHallDictTS = mtimeHallDictTMapper.selectList(hallWrapper);
        String seatAddress = mtimeHallDictTS.get(0).getSeatAddress();
        //File file = areaRes.getFile();
        File file = new File(this.getClass().getResource("/static/" + seatAddress).getPath());
        FileInputStream fileInputStream = new FileInputStream(file);
        //String s1 = IOUtils.toString(areaRes.getInputStream(), Charset.forName("UTF-8"));
        String s1 = IOUtils.toString(fileInputStream, Charset.forName("UTF-8"));
        String ids = (String) JSONPath.read(s1, "$ids");
        String[] split1 = ids.split(",");
        //File file = new File("static\\" + seatAddress);
        /*FileReader reader = new FileReader(file);*/
        /*BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";
        while ((json =bufferedReader.readLine()) != null) {
            stringBuilder.append(json + "\n");
        }
        bufferedReader.close();
        String s1 = stringBuilder.toString();
        Seats seats = parserSeats(s1);*/
        //String ids = seats.getIds();
        for (String s : split) {
            for (String s2 : split1) {
                if (s2.equals(s)){
                    isNotExist = false;
                    break;
                }
            }
        }
        if (isNotExist){
            baseResVO.setMsg("该座位不存在");
            baseResVO.setStatus(1);
            return baseResVO;
        }
        boolean isSold = false;
        for (String s : split) {
            for (String s2 : split2) {
                if (s2.equals(s)){
                    isSold = true;
                    break;
                }
            }
        }
        if (isSold){
            baseResVO.setMsg("该座位已被购买");
            baseResVO.setStatus(1);
            return baseResVO;
        }
        EntityWrapper<MtimeCinemaT> cinemaWrapper = new EntityWrapper<>();
        EntityWrapper<MtimeHallFilmInfoT> filmWrapper = new EntityWrapper<>();
        cinemaWrapper.eq("UUID",mtimeFieldT.getCinemaId());
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(cinemaWrapper);
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTS.get(0);
        filmWrapper.eq("film_id",mtimeFieldT.getFilmId());
        List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(filmWrapper);
        MtimeHallFilmInfoT mtimeHallFilmInfoT = mtimeHallFilmInfoTS.get(0);
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setCinemaId(mtimeCinemaT.getUuid());
        moocOrderT.setFilmId(mtimeHallFilmInfoT.getUuid());
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setFilmPrice(Double.valueOf(mtimeFieldT.getPrice()));
        moocOrderT.setOrderPrice((double) (soldSeats.split(",").length * mtimeFieldT.getPrice()));
        moocOrderT.setOrderTime(new Date());
        moocOrderT.setOrderStatus(0);
        moocOrderT.setOrderUser(uuid);
        orderMapper.insert(moocOrderT);
        orderVo.setOrderId(moocOrderT.getUuid());
        orderVo.setCinemaName(mtimeCinemaT.getCinemaName());
        orderVo.setFieldTime("今日" + mtimeFieldT.getBeginTime());
        orderVo.setFilmName(mtimeHallFilmInfoT.getFilmName());
        orderVo.setSeatsName(moocOrderT.getSeatsName());
        orderVo.setOrderPrice(String.valueOf(moocOrderT.getOrderPrice()));
        Date date = new Date();
        orderVo.setOrderTimestamp(date.toString());
        baseResVO.setStatus(0);
        baseResVO.setData(orderVo);
        return baseResVO;
    }

    @Override
    public BaseResVO getOrderInfo(Integer nowPage, Integer pageSize, Integer uuid) {
        PageHelper.startPage(nowPage,pageSize);
        List<OrderVo> orderVos = orderMapper.orderInfo(uuid);
        BaseResVO baseResVO = new BaseResVO();
        if (orderVos.size() == 0){
            baseResVO.setMsg("订单列表为空");
            baseResVO.setStatus(1);
            return baseResVO;
        }
        for (OrderVo orderVo : orderVos) {
            orderVo.setFieldTime("今日" + orderVo.getFieldTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = null;
            try {
                parse = sdf.parse(orderVo.getOrderTimestamp());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time = parse.getTime() / 1000;
            orderVo.setOrderTimestamp(String.valueOf(time));
            if (orderVo.getOrderStatus().equals("1")){
                orderVo.setOrderStatus("已完成");
            } else if (orderVo.getOrderStatus().equals("0")){
                orderVo.setOrderStatus("未付款");
            } else if (orderVo.getOrderStatus().equals("2")){
                orderVo.setOrderStatus("已取消");
            }
        }
        baseResVO.setStatus(0);
        baseResVO.setData(orderVos);
        return baseResVO;
    }
/*
    private Seats parserSeats(String s1) {
        JSONObject object = new JSONObject();

        return seats;
    }*/
}
