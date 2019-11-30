package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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


    @Override
    public Integer register(UserVO userVO, String password) {
        EntityWrapper<MtimeUserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name",userVO.getUserName());
        List<MtimeUserT> users = userMapper.selectList(wrapper);
        if (users.size()>0) {return 1;}
        else {
            MtimeUserT mtimeUserT = new MtimeUserT();
            BeanUtils.copyProperties(userVO,mtimeUserT);
            mtimeUserT.setUserPwd(password);
            userMapper.insert(mtimeUserT);
        }
        return 0;
    }

    @Override
    public Integer check(String username) {
        EntityWrapper<MtimeUserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name",username);
        List<MtimeUserT> users = userMapper.selectList(wrapper);
        if (users.size()>0) {return 1;}
        else {
            return 0;
        }

    }

    @Override
    public void updateUserInfo(UserVO userVO) {
        MtimeUserT user = new MtimeUserT();
        BeanUtils.copyProperties(user,userVO);
        userMapper.updateById(user);

    }
}
