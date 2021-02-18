# spring boot cache

需要注意几点：
1. @CachePut 要求方法必须要有返回值才能正常缓存到redis，否则会缓存null
2. 默认的序列化会有16进制字符，可以通过cacheManager解决
3. 默认缓存value的格式可以通过配置redisTemplate定义，比如自定义为json格式
4. 默认的key生成规则会有问题，如果hashCode相同，会造成缓存冲突