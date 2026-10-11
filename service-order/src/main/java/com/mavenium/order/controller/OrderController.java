package com.mavenium.order.controller;

import cn.hutool.core.lang.Dict;
import com.mavenium.common.entity.ResponseResult;
import com.mavenium.order.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务接口（统一返回 ResponseResult）
 *
 * @author Mavenium
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserFeignClient userFeignClient;

    /**
     * 查询订单（演示接口）
     * 数据源就绪后，可替换为 orderMapper.selectById(id) 查询真实数据
     */
    @GetMapping("/{id}")
    public ResponseResult<Dict> getOrder(@PathVariable Long id) {
        return ResponseResult.ok(Dict.create()
                .set("orderId", id)
                .set("orderNo", "NO-" + id)
                .set("service", "service-order"));
    }

    /**
     * 订单服务通过 OpenFeign 调用用户服务，验证服务间调用
     */
    @GetMapping("/{id}/user")
    public ResponseResult<Dict> getOrderUser(@PathVariable Long id) {
        return userFeignClient.getUser(id);
    }
}
