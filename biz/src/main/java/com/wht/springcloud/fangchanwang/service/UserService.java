package com.wht.springcloud.fangchanwang.service;

import com.wht.springcloud.fangchanwang.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> getAllUsers();

    boolean addAccount(UserModel userModel);

    boolean enable(String key);

    UserModel auth(String username, String password);
}
