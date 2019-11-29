package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.service.UserService;
import com.stylefeng.guns.rest.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    MtimeUserTMapper userMapper;

    @Override
    public UserVO login(String username, String password) {
        EntityWrapper<MtimeUserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name", username).eq("user_pwd", password);
        List<MtimeUserT> users = userMapper.selectList(wrapper);
        if (users.size() > 0) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(users.get(0), userVO);
            return userVO;
        }
        return null;
    }
}