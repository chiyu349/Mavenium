package com.mavenium.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mavenium.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper（MyBatis-Plus 基础 CRUD）
 *
 * @author Mavenium
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
