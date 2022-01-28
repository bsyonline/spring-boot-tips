
actuator 默认访问地址 [http://localhost:8080/actuator/](http://localhost:8080/actuator/)

可以修改 actuator 地址
```
management:
  endpoints:
    web:
      base-path: /myactuator
```
修改之后通过 [http://localhost:8080/myactuator/](http://localhost:8080/myactuator/) 访问。

### endpoint
默认打开的 endpoint 有 /health 和 /info ，使用下边的配置可以打开所有 endpoint 。
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

使用 @Endpoint 自定义一个 endpoint
```
@Component
@Endpoint(id = "custom")
public class CustomEndpoint {}
```

### HealthIndicator
actuator 的健康检查 [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) 显示的是所有 HealthIndicator 的汇总信息，UP 是健康，DOWN 是不健康。
actuator 内置了一些 HealthIndicator ，比如 DiskSpaceHealthIndicator。如果 classpath 中包含某种依赖，springboot 会自动注册 HealthIndicator ，比如 DataSourceHealthIndicator 。
可以通过 [http://localhost:8080/actuator/health/xxx](http://localhost:8080/actuator/health/xxx) 查看具体的健康状态，同时需要开启 detail 显示。
```aidl
management:
  endpoint:
    health:
      show-details: always
```
如果没有 springboot 内置的 HealthIndicator ，可以自定义 HealthIndicator 。 可以实现 HealthIndicator 接口或是继承 AbstractHealthIndicator 。

禁用一个 HealthIndicator ，比如 redis 。
```
management:
  health:
    redis:
      enabled: false
```
