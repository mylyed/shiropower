package io.github.mylyed.shiropower.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomSpringCacheManager implements CacheManager {


    public ConcurrentMap<String, CustomSpringCache> allcache = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        System.out.println("获取缓存" + name);
        if (!allcache.containsKey(name)) {
            allcache.put(name, new CustomSpringCache(name));
        }
        return allcache.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return allcache.keySet();
    }
}