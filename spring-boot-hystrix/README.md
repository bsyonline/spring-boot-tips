

request cache
在一个request context中可以对相同command参数的执行结果缓存。
1. 注册context
```java
@Bean
public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
            new HystrixRequestContextFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
}
```
2. 重写command的cacheKey
```java
@Override
protected String getCacheKey() {
    return "user_" + id;
}
```
3. 清除缓存
```java
public void flushCache(Long id) {
    HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("GetUserCommand"),
            HystrixConcurrencyStrategyDefault.getInstance()).clear("user_" + id);
}
```

fallback
fallback的几种情况：
1. 短路器打开
2. 线程池或信号量满
3. 执行超时
4. 执行异常

circuit breaker 短路器
工作原理
在一个时间窗口（10s）内，经过短路器的流量达到一定数量（默认20）则进行统计判断是否开启短路器，如果流量没有达到，就不会打开短路器。
如果短路器统计的异常占比超过阈值（默认50%），短路器就会打开。
短路器开启，所有的请求都会直接走fallback。
再经过一段时间（默认5秒），短路器的状态会变成half-open，让1个请求经过，如果成功，短路器就会关闭。

配置
```java
Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserService"))
        .withFallbackIsolationSemaphoreMaxConcurrentRequests(10) // fallback的并发数
        .withCircuitBreakerEnabled(true) // 是否开启短路器，默认是true
        .withExecutionTimeoutInMilliseconds(1000) // 超时
        .withMetricsRollingStatisticalWindowBuckets(10) // 统计的bucket数量，默认10秒10个bucket，即1秒1个bucket
        .withMetricsRollingStatisticalWindowInMilliseconds(10000) // 统计窗口时长，默认10秒
        .withCircuitBreakerRequestVolumeThreshold(20) // 滑动窗口中有多少个请求才开始统计，默认20
        .withCircuitBreakerSleepWindowInMilliseconds(5000) // 开启短路器后，经过多长时间短路器变成half-open，默认5s
        .withCircuitBreakerErrorThresholdPercentage(50) // 经过短路器的异常请求比例，默认50
) 
```

timeout
```java
.withExecutionTimeoutInMilliseconds(1000)
```

HystrixCollapser
可以将多个command合并到一个command中执行，发送一次网络请求，以提高并发，但是会有一定的响应延迟增加。
合并的级别：
global context: 全局的
user request context: 一个请求线程内的多个command进行合并
object modeling：基于对象的请求合并

Collapser配置
```java
Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("GetUserInfosCollapser"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                   .withMaxRequestsInBatch(100)
                   .withTimerDelayInMilliseconds(20))
```