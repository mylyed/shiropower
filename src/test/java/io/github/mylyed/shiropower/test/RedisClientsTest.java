package io.github.mylyed.shiropower.test;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

/**
 * redis 测试
 * Created by lilei on 2018/8/10.
 */
public class RedisClientsTest {


    JedisPool jedisPool;

    @Before
    public void init() {
        System.out.println("初始化");
        jedisPool = new JedisPool("192.168.99.101", 6379);
    }

    @Test
    public void test() {

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("aaa", "aaa");
            //设置key的超时时间 单位是秒
            jedis.expire("aaa", 60);

            TimeUnit.SECONDS.toSeconds(5);


            String aaa = jedis.get("aaa");
            System.out.println(aaa);

        }
    }


}
