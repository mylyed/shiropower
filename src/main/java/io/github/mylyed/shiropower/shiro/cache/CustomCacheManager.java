package io.github.mylyed.shiropower.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 自定义缓存管理器
 * 可以缓存用户的授权信息
 * 不然会重复调用获取权限的方法
 * Created by lilei on 2018/8/10.
 */
public class CustomCacheManager implements CacheManager {


    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return null;
    }
}

