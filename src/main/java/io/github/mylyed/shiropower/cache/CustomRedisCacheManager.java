package io.github.mylyed.shiropower.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomRedisCacheManager implements CacheManager {

    JedisPool jedisPool;


    public CustomRedisCacheManager(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public ConcurrentMap<String, Cache> allcache = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        if (!allcache.containsKey(name)) {
            allcache.put(name, new CustomRedisCache(name, jedisPool));
        }
        return allcache.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return allcache.keySet();
    }
}