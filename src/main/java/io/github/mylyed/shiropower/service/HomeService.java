package io.github.mylyed.shiropower.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by lilei on 2018/8/13.
 */
@Service
@CacheConfig(cacheNames = "asdsadsadtestcache")
public class HomeService {


    @Cacheable()
    public String home(String str) {
        System.out.println("调用home方法");
        return "参数是" + str;
    }

}
