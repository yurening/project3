package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.vo.UserVO;

public interface UserService {
    UserVO login(String username, String password);
}
