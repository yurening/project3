package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-12-03
 */
public interface MtimePromoMapper extends BaseMapper<MtimePromo> {

    Integer decreaseStock(@Param("promoId")Integer promoId, @Param("amount")Integer amount);
}
