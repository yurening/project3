package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.core.exception.ServiceExceptionEnum;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.StockLogStatus;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoStock;
import com.stylefeng.guns.rest.common.persistence.model.MtimeStockLog;
import com.stylefeng.guns.rest.consistant.RedisPrefixConsistant;
import com.stylefeng.guns.rest.dto.CinemaDTO;
import com.stylefeng.guns.rest.mq.MqProducer;
import com.stylefeng.guns.rest.service.cinemalzy.IMtimeCinemaTService;
import com.stylefeng.guns.rest.vo.BaseResVO;
import com.stylefeng.guns.rest.vo.PromoForCinemaVO;
import com.stylefeng.guns.rest.vo.PromoVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {
    private static final String PROMO_STOCK_PREFIX = "promo_stock_prefix_";

    @Reference(interfaceClass = IMtimeCinemaTService.class, check = false)
    IMtimeCinemaTService iMtimeCinemaTService;

    @Autowired
    MtimePromoMapper mtimePromoMapper;

    @Autowired
    MtimePromoStockMapper mtimePromoStockMapper;

    @Autowired
    MtimePromoOrderMapper mtimePromoOrderMapper;

    @Autowired
    MtimeStockLogMapper mtimeStockLogMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MqProducer mqProducer;

    private ExecutorService executorService;

    public static final Integer PROMO_TOKEN_AMOUNT_TIMES = 5;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(100);
    }

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
                /*EntityWrapper<MtimePromoStock> stockEntityWrapper = new EntityWrapper<>();
                stockEntityWrapper.eq("promo_id",mtimePromo.getUuid());
                List<MtimePromoStock> mtimePromoStocks = mtimePromoStockMapper.selectList(stockEntityWrapper);
                MtimePromoStock mtimePromoStock = mtimePromoStocks.get(0);
                promo.setStock(mtimePromoStock.getStock());*/
                Integer stock= (Integer) redisTemplate.opsForValue().get(PROMO_STOCK_PREFIX + mtimePromo.getUuid()/*String.valueOf(mtimePromo.getUuid())*/);
                promo.setStock(stock);

                dataList.add(promo);
            }
        }

        BaseResVO baseResVO = new BaseResVO();
        baseResVO.setData(dataList);
        baseResVO.setStatus(0);
        return baseResVO;
    }

    @Override
    public Boolean createPromo(Integer promoId, Integer amount, Integer userId, String stockLogId) {
        return mqProducer.sendStockMessageInTransaction(promoId, amount, userId, stockLogId);
    }

    @Override
    public void publishPromoStock() {
        List<MtimePromoStock> promoStocks = mtimePromoStockMapper.selectList(new EntityWrapper<>());
        for (MtimePromoStock stock : promoStocks) {

            String stockKey = PROMO_STOCK_PREFIX+stock.getPromoId();
            if(!redisTemplate.hasKey(stockKey)) {
                redisTemplate.opsForValue().set(PROMO_STOCK_PREFIX+stock.getPromoId(), stock.getStock());
            }

            String tokenAmountKey = RedisPrefixConsistant.PROMO_TOKEN_AMOUNT_LIMIT_PROMOID + stock.getPromoId();
            if (!redisTemplate.hasKey(tokenAmountKey)) {
                Integer tokenAmount = stock.getStock() * PROMO_TOKEN_AMOUNT_TIMES;
                redisTemplate.opsForValue().set(tokenAmountKey, tokenAmount);
            }
        }
    }

    @Override
    public String initPromoStockLog(Integer promoId, Integer amount) {
        MtimeStockLog mtimeStockLog = new MtimeStockLog();
        String stockLogId = UUID.randomUUID().toString().replaceAll("-", "").substring(16);
        mtimeStockLog.setUuid(stockLogId);
        mtimeStockLog.setStatus(StockLogStatus.INIT.getCode());
        mtimeStockLog.setPromoId(promoId);
        mtimeStockLog.setAmount(amount);
        Integer insert = mtimeStockLogMapper.insert(mtimeStockLog);
        if (insert <= 0) {
            return null;
        }
        return stockLogId;
    }

    @Transactional
    @Override
    public void createPromoOrder(HashMap hashMap) throws Exception {
        Integer promoId = (Integer) hashMap.get("promoId");
        Integer amount = (Integer) hashMap.get("amount");
        Integer userId = (Integer) hashMap.get("userId");
        String stockLogId = (String) hashMap.get("stockLogId");
        Integer decreaseAmount = amount * -1;
//        Long promo = redisTemplate.opsForHash().increment("promo", promoId+"", decreaseAmount);
        Long promo = redisTemplate.opsForValue().increment(PROMO_STOCK_PREFIX + promoId, decreaseAmount);
        if (promo < 0) {
//            redisTemplate.opsForHash().increment("promo", promoId+"", amount);
            redisTemplate.opsForValue().increment(PROMO_STOCK_PREFIX + promoId, amount);
            executorService.submit(() -> mtimeStockLogMapper.updateStatusById(stockLogId, StockLogStatus.FAIL.getCode()));
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }
        if (promo == 0) {
            String key = RedisPrefixConsistant.PROMO_SOLDOUT_PROMOID + promoId;
            redisTemplate.opsForValue().set(key, "soldout");
            redisTemplate.expire(key, 2, TimeUnit.HOURS);
        }

        //根据活动id查活动表格
        MtimePromo mtimePromo = mtimePromoMapper.selectById(promoId);

        //生成兑换码
        String exchangeCode = UUID.randomUUID().toString().replace("-", "");



        MtimePromoOrder promoOrder = new MtimePromoOrder();
        promoOrder.setUserId(userId);
        promoOrder.setCinemaId(mtimePromo.getCinemaId());
        promoOrder.setAmount(amount);
        promoOrder.setPrice(mtimePromo.getPrice().multiply(new BigDecimal(amount)));
        promoOrder.setStartTime(mtimePromo.getStartTime());
        promoOrder.setEndTime(mtimePromo.getEndTime());
        promoOrder.setExchangeCode(exchangeCode);

        Integer insert = mtimePromoOrderMapper.insert(promoOrder);
        if (insert < 1) {
            executorService.submit(() -> mtimeStockLogMapper.updateStatusById(stockLogId, StockLogStatus.FAIL.getCode()));
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }
        mtimeStockLogMapper.updateStatusById(stockLogId, StockLogStatus.SUCCESS.getCode());
    }

    @Override
    public Integer getStatusByStockLogId(String stockLogId) {
        return mtimeStockLogMapper.selectStatusById(stockLogId);
    }

    @Override
    public PromoVO getPromoById(Integer promoId) {
        MtimePromo mtimePromo = mtimePromoMapper.selectById(promoId);
        if (mtimePromo == null) return null;
        PromoVO promoVO = new PromoVO();
        promoVO.setStatus(mtimePromo.getStatus());
        return promoVO;
    }

    @Override
    public String generateToken(Integer promoId, Integer userId) {
        String tokenAmountKey = RedisPrefixConsistant.PROMO_TOKEN_AMOUNT_LIMIT_PROMOID + promoId;
        Long tokenAmount = redisTemplate.opsForValue().increment(tokenAmountKey, -1);
        if (tokenAmount < 0) {
            return null;
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(18);
        String tokenKey = String.format(RedisPrefixConsistant.PROMO_USER_TOKEN_PROMOID, promoId, userId);
        redisTemplate.opsForValue().set(tokenKey, uuid);
        return uuid;
    }

}
