package com.stylefeng.guns.rest.service.cinemalzy;

import com.stylefeng.guns.rest.dto.CinemaDTO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaBaseVO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaConditionVO;
import com.stylefeng.guns.rest.vo.cinemavolzy.CinemaInfoVO;
import java.util.List;

/**
 * <p>
 * 影院信息表 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-11-28
 */
public interface IMtimeCinemaTService {
    CinemaBaseVO<List<CinemaInfoVO>> getCinemasItem(Integer brandId, Integer hallType,Integer halltypeId, Integer areaId, Integer pageSize, Integer nowPage);

    CinemaBaseVO<CinemaConditionVO> getCinemaCondition(Integer brandId, Integer areaId, Integer hallType);

    //yzn：通过品牌id和地区id获取对应的电影院信息
    List<CinemaDTO> getCinemaForPromo(Integer brandId, Integer areaId, String hallType,Integer pageSize, Integer nowPage);
}
