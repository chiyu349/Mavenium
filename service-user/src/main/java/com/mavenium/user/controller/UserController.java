package com.mavenium.user.controller;

import cn.hutool.core.lang.Dict;
import com.mavenium.common.entity.ResponseResult;
import com.mavenium.common.exception.BusinessException;
import com.mavenium.user.entity.User;
import com.mavenium.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户服务接口（统一返回 ResponseResult）
 *
 * @author Mavenium
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 查询用户（演示接口）
     * 数据源就绪后，可替换为 userMapper.selectById(id) 查询真实数据
     */
    @GetMapping("/{id}")
    public ResponseResult<Dict> getUser(@PathVariable Long id) {
        return ResponseResult.ok(Dict.create()
                .set("id", id)
                .set("name", "Mavenium-User-" + id)
                .set("service", "service-user"));
    }

    /**
     * 演示：业务异常由全局异常处理器转换为统一响应体
     * 返回：{"code":500,"message":"演示业务异常：用户数据不存在","success":false,"timestamp":...}
     */
    @GetMapping("/demo-error")
    public ResponseResult<Void> demoError() {
        throw new BusinessException("演示业务异常：用户数据不存在");
    }

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<List<User>> list() {
        return userService.queryAll();
    }
}
