

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
1. 断路器打开
2. 线程池或信号量满
3. 执行超时
4. 执行异常