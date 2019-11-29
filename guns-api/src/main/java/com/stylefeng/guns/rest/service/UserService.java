package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.UserVO;

public interface UserService {
    UserVO login(String username, String password);


    Integer register(UserVO userVO,String password);

    Integer check(String username);

    void updateUserInfo(UserVO userVO);
}
