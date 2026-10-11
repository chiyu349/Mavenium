# Mavenium 微服务工程

基于 **Spring Boot 3.2.x** 的多模块 Maven 微服务脚手架：一个父工程 + 公共模块 + 两个微服务子模块。

## 项目结构

```
Mavenium/                          # 父工程（聚合 + 依赖版本统一管理）
├── pom.xml
├── service-common/                # 公共模块（不启动，被其他服务依赖）
│   ├── pom.xml
│   └── src/main/java/com/mavenium/common/
│       ├── entity/ResponseResult.java      # 统一响应体
│       ├── enums/ResultCode.java           # 统一业务状态码
│       └── exception/                      # BusinessException + GlobalExceptionHandler
├── service-user/                  # 用户微服务（端口 8081）
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/mavenium/user/
│       │   ├── UserServiceApplication.java
│       │   ├── controller/UserController.java
│       │   ├── entity/User.java
│       │   └── mapper/UserMapper.java
│       └── resources/application.yml
├── service-order/                 # 订单微服务（端口 8082，通过 OpenFeign 调用 user-service）
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/mavenium/order/
│       │   ├── OrderServiceApplication.java
│       │   ├── controller/OrderController.java
│       │   └── feign/UserFeignClient.java
│       └── resources/application.yml
└── README.md
```

## 版本矩阵（父工程统一管理）

| 组件 | 版本 | 说明 |
| ---- | ---- | ---- |
| Spring Boot | 3.2.12 | 3.2.x 最终版本，稳定生产推荐 |
| Java | 17 | Spring Boot 3 最低要求 JDK 17+ |
| Spring Cloud | 2023.0.4 (Leyton) | 对应 Spring Boot 3.2.x |
| Spring Cloud Alibaba | 2023.0.1.0 | 适配 Boot 3.2.x，Nacos 注册/配置中心 |
| MyBatis-Plus | 3.5.12 | 使用 boot3 专用 Starter |
| Druid | 1.2.28 | 使用 boot3 专用 Starter |
| springdoc-openapi | 2.6.0 | Swagger 3 / OpenAPI 3，适配 Boot 3.2 |
| Hutool | 5.8.32 | 工具类库 |
| Fastjson2 | 2.0.53 | JSON 序列化 |
| Sa-Token | 1.37.0 | 权限认证（已纳入父工程管理，按需引入） |

## 统一响应体（service-common）

所有接口统一返回 `ResponseResult`，前端只判断 `success / code`。约定：**HTTP 状态码固定 200，业务结果通过 body.code 表达**。

```java
// 成功
return ResponseResult.ok(data);              // {"code":200,"message":"操作成功","success":true,"data":...,"timestamp":...}
return ResponseResult.ok();                  // 无数据时省略 data（@JsonInclude(NON_NULL)）
return ResponseResult.ok("自定义提示", data);

// 失败（业务层）
return ResponseResult.fail("xxx");                    // 默认 code=500
return ResponseResult.fail(ResultCode.UNAUTHORIZED);  // code=401
return ResponseResult.fail(400, "xxx");

// 业务异常：抛出自定义异常，由 GlobalExceptionHandler 统一转换为失败响应
throw new BusinessException("用户数据不存在");
```

参数校验（`@Valid`）失败、未知异常也由 `GlobalExceptionHandler` 转换为统一格式。

## 常用命令

```bash
# 全量编译（在 Mavenium 根目录执行，会同时构建所有模块）
mvn clean compile

# 打包（跳过测试）
mvn clean package -DskipTests

# 只构建某个子模块（-am 表示同时构建其依赖，如 service-common）
mvn -pl service-user -am clean package -DskipTests
```

## 启动步骤

1. **（可选）启动 Nacos**：注册中心地址默认 `127.0.0.1:8848`；`optional:` 前缀保证 Nacos 未启动时本地服务也能正常启动。
2. **（可选）准备 MySQL**：创建数据库 `mavenium`，并按需调整 `application.yml` 中的账号密码（当前默认 root/root）。
3. **启动服务**：分别运行 `UserServiceApplication`（8081）和 `OrderServiceApplication`（8082）。
4. **验证**：
   - 用户服务：`http://localhost:8081/user/1`
   - 异常演示：`http://localhost:8081/user/demo-error`
   - 订单服务：`http://localhost:8082/order/1`
   - 服务间调用（订单 → 用户）：`http://localhost:8082/order/1/user`
   - 接口文档：`http://localhost:8081/swagger-ui/index.html`、`http://localhost:8082/swagger-ui/index.html`
   - 健康检查：`http://localhost:8081/actuator/health`

## 注意事项

- Spring Boot 3 使用 `jakarta.*` 包名，老代码中的 `javax.*` 需替换。
- MyBatis-Plus、Druid 均需使用 `*-spring-boot3-starter` 专用坐标。
- Spring Cloud 2023 已移除 Ribbon，服务调用需引入 `spring-cloud-starter-loadbalancer`。
- 所有第三方组件版本只需在父工程 `pom.xml` 的 `<properties>` / `<dependencyManagement>` 中修改一次，子模块统一生效。
- 各服务启动类使用 `@SpringBootApplication(scanBasePackages = "com.mavenium")`，确保 service-common 中的全局异常处理器被扫描注册。
