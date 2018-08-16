package io.github.mylyed.shiropower.config;

import io.github.mylyed.shiropower.cache.CustomRedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * spring缓存配置文件
 *
 * @author lilei
 */
@Configuration
@EnableCaching
@Slf4j
public class CachingConfig extends CachingConfigurerSupport {


//    @Bean
//    public CacheManager cacheManager() {
//        System.out.println("初始化缓存管理器");
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("home")));//注册名为sampleCache的缓存
//        return cacheManager;
//    }


    @Bean
    public CacheManager cacheManager() {
        log.debug("初始化缓存管理器");
        CustomRedisCacheManager cacheManager = new CustomRedisCacheManager(new JedisPool("172.16.8.113", 6379));
        return cacheManager;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        log.debug("初始化缓存key生成器");
        KeyGenerator keyGenerator = new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                log.debug("生成key=" + method.getName());
//                return generateKey(params);
                return method.getName() + Arrays.toString(params);
            }
//            /**
//             * Generate a key based on the specified parameters.
//             */
//            public Object generateKey(Object... params) {
//                if (params.length == 0) {
//                    return SimpleKey.EMPTY;
//                }
//                if (params.length == 1) {
//                    Object param = params[0];
//                    if (param != null && !param.getClass().isArray()) {
//                        return "test11111" + param.toString();
//                    }
//                }
//                return "test" + Arrays.toString(params);
//            }
        };
        return keyGenerator;
    }


}