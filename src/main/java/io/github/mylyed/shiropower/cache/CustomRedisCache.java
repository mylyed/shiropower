package io.github.mylyed.shiropower.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.lang.Nullable;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.Callable;

/**
 * 自定义redis缓存
 */
@Slf4j
public class CustomRedisCache implements Cache {


    private JedisPool jedisPool;


    private String cacheName;

    public CustomRedisCache(String cacheName, JedisPool jedisPool) {

        this.cacheName = cacheName;
        this.jedisPool = jedisPool;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }


    public byte[] generatorKey(Object key) {
        String keyStr = (cacheName + ":" + key.toString());

        log.debug("redis key=" + keyStr);
        return keyStr.getBytes();
    }

    @Override
    public ValueWrapper get(Object key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(generatorKey(key));
            return toValueWrapper(SerializationUtils.deserialize(bytes));
        }
    }

    @Override
    public <T> T get(Object key, @Nullable Class<T> type) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(generatorKey(key));
            return (T) SerializationUtils.deserialize(bytes);
        }
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(generatorKey(key));
            if (bytes != null) {
                return (T) SerializationUtils.deserialize(bytes);
            } else {
                return valueLoader.call();
            }
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(generatorKey(key), SerializationUtils.serialize(value));
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keybts = generatorKey(key);
            byte[] bytes = jedis.get(keybts);
            if (bytes == null) {
                jedis.set(keybts, SerializationUtils.serialize(value));
                return null;
            } else {
                return toValueWrapper(SerializationUtils.deserialize(bytes));
            }

        }

    }

    @Override
    public void evict(Object key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = (generatorKey(key));
            jedis.del(bytes);
        }
    }

    @Override
    public void clear() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
        }
    }

    @Nullable
    protected ValueWrapper toValueWrapper(@Nullable Object storeValue) {
        return (storeValue != null ? new SimpleValueWrapper(storeValue) : null);
    }

}