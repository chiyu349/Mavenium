package com.mavenium.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mavenium.common.entity.ResponseResult;
import com.mavenium.user.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    ResponseResult<List<User>> queryAll();
}
