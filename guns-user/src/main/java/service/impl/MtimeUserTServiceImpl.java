package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;

import org.springframework.stereotype.Component;
import service.IMtimeUserTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author StephenAndJerry
 * @since 2019-11-28
 */
@Component
@Service(interfaceClass = IMtimeUserTService.class)
public class MtimeUserTServiceImpl extends ServiceImpl<MtimeUserTMapper, MtimeUserT> implements IMtimeUserTService {

}
