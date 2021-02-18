# spring boot cache annotation

StringRedisTemplate 继承自 RedisTemplate<String, String> ，如果操作string推荐使用。RedisTemplate<K, V> 可以支持泛型，如果value不是string，可以使用RedisTemplate，比如使用二进制对value进行序列化。