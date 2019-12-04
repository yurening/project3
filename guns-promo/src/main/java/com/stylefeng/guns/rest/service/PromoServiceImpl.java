package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoStock;
import com.stylefeng.guns.rest.dto.CinemaDTO;
import com.stylefeng.guns.rest.service.cinemalzy.IMtimeCinemaTService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.PromoForCinemaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {
    @Reference(interfaceClass = IMtimeCinemaTService.class)
    IMtimeCinemaTService iMtimeCinemaTService;

    @Autowired
    MtimePromoMapper mtimePromoMapper;

    @Autowired
    MtimePromoStockMapper mtimePromoStockMapper;

    @Autowired
    MtimePromoOrderMapper mtimePromoOrderMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取活动信息
     * @param brandId
     * @param hallType
     * @param areaId
     * @param pageSize
     * @param nowPage
     * @return
     */
    @Override
    public BaseResVO getPromo(Integer brandId, String hallType,
                              Integer areaId, Integer pageSize, Integer nowPage) {
        List<CinemaDTO> cinemaForPromo = iMtimeCinemaTService.getCinemaForPromo(brandId, areaId,
                hallType,pageSize,nowPage);
        List<PromoForCinemaVO> dataList = new ArrayList<>();
        for (CinemaDTO cinemaDTO : cinemaForPromo) {
            Integer cinemaId = cinemaDTO.getCinemaId();
            EntityWrapper<MtimePromo> mtimePromoEntityWrapper = new EntityWrapper<>();
            mtimePromoEntityWrapper.eq("cinema_id",cinemaId);
            List<MtimePromo> mtimePromos = mtimePromoMapper.selectList(mtimePromoEntityWrapper);
            //MtimePromo mtimePromo = mtimePromos.get(0);
            for (MtimePromo mtimePromo : mtimePromos) {
                PromoForCinemaVO promo = new PromoForCinemaVO();
                promo.setCinemaAddress(cinemaDTO.getCinemaAddress());
                promo.setCinemaId(cinemaDTO.getCinemaId());
                promo.setCinemaName(cinemaDTO.getCinemaName());
                promo.setDescription(mtimePromo.getDescription());
                promo.setEndTime(mtimePromo.getEndTime());
                promo.setImgAddress(cinemaDTO.getImgAddress());
                promo.setPrice(mtimePromo.getPrice());
                promo.setStartTime(mtimePromo.getStartTime());
                promo.setStatus(mtimePromo.getStatus());
                promo.setUuid(mtimePromo.getUuid());

                //库存
                EntityWrapper<MtimePromoStock> stockEntityWrapper = new EntityWrapper<>();
                stockEntityWrapper.eq("promo_id",mtimePromo.getUuid());
                List<MtimePromoStock> mtimePromoStocks = mtimePromoStockMapper.selectList(stockEntityWrapper);
                MtimePromoStock mtimePromoStock = mtimePromoStocks.get(0);
                promo.setStock(mtimePromoStock.getStock());

                dataList.add(promo);
            }
        }

        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setData(dataList);
        baseResVO.setStatus(0);
        return baseResVO;
    }

    @Override
    public BaseResVO createPromo(Integer promoId, Integer amount, Integer userId) {
        //根据活动id查活动表格
        MtimePromo mtimePromo = mtimePromoMapper.selectById(promoId);

        //生成兑换码部分
        String replace = UUID.randomUUID().toString().replace("-", "");

        MtimePromoOrder promoOrder = new MtimePromoOrder();
        promoOrder.setUserId(userId);
        promoOrder.setCinemaId(mtimePromo.getCinemaId());
        promoOrder.setAmount(amount);
        promoOrder.setPrice(mtimePromo.getPrice());
        promoOrder.setStartTime(mtimePromo.getStartTime());
        promoOrder.setCreateTime(new Date());
        promoOrder.setEndTime(mtimePromo.getEndTime());
        promoOrder.setExchangeCode(replace);

        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setStatus(0);
        baseResVO.setMsg("下单成功");

        return baseResVO;
    }

    @Override
    public void publishPromoStock() {
        List<MtimePromoStock> promoStocks = mtimePromoStockMapper.selectList(new EntityWrapper<>());
        for (MtimePromoStock stock : promoStocks) {
            if (!redisTemplate.opsForHash().hasKey("promo", stock.getPromoId()+"")) {
                redisTemplate.opsForHash().put("promo", stock.getPromoId()+"", stock.getStock()+"");
            }
        }
    }
}
