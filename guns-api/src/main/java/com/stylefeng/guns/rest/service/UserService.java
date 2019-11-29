package com.stylefeng.guns.rest.service;

public interface UserService {
    Integer login(String username, String password);

    Integer register(String username, String password);
}
