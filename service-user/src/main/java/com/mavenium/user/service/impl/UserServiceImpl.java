package com.mavenium.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mavenium.common.entity.ResponseResult;
import com.mavenium.user.entity.User;
import com.mavenium.user.mapper.UserMapper;
import com.mavenium.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult<List<User>> queryAll() {
        return ResponseResult.ok(list());
    }
}
