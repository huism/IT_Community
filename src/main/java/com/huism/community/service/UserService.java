package com.huism.community.service;

import com.huism.community.mapper.UserMapper;
import com.huism.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // 登录时，插入或更新user
    public void insertOrUpdate(User user) {

        User existedUser = userMapper.findByAccountId(user.getAccountId());

        if(existedUser != null){
            // 更新
            existedUser.setGmtModified(System.currentTimeMillis());
            existedUser.setAvatarUrl(user.getAvatarUrl());
            existedUser.setName(user.getName());
            existedUser.setToken(user.getToken());
            userMapper.update(existedUser);
        }else{
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }

    }
}
