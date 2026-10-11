package com.mavenium.order.feign;

import cn.hutool.core.lang.Dict;
import com.mavenium.common.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务 OpenFeign 客户端
 * name 对应注册中心中 user-service 的服务名；返回类型与 user-service 接口保持一致（统一 ResponseResult）
 *
 * @author Mavenium
 */
@FeignClient(name = "user-service")
public interface UserFeignClient {

    /**
     * 调用用户服务的 GET /user/{id}
     */
    @GetMapping("/user/{id}")
    ResponseResult<Dict> getUser(@PathVariable("id") Long id);
}
