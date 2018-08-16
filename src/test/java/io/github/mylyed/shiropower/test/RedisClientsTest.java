package io.github.mylyed.shiropower.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * redis 测试
 * Created by lilei on 2018/8/10.
 */

public class RedisClientsTest {


    JedisPool jedisPool;

    @Before
    public void init() {
        System.out.println("初始化");
        jedisPool = new JedisPool("172.16.8.113", 6379);
    }

    @Test
    public void test() {

        try (Jedis jedis = jedisPool.getResource()) {


            Map<byte[], byte[]> map = new HashMap();
            map.put("aaa".getBytes(), "测试字符串".getBytes());
            map.put("nnn".getBytes(), "asdsadsad".getBytes());

            jedis.hmset(("np:bbb").getBytes(), map);
            jedis.set(("np:ccc"), "bbasd");
            //设置key的超时时间 单位是秒
            // jedis.expire("aaa", 60);

            // TimeUnit.SECONDS.toSeconds(5);


        }
    }

    @Test
    public void test2() {

        try (Jedis jedis = jedisPool.getResource()) {

            Map<String, String> aaa = jedis.hgetAll("bbb");
            System.out.println(aaa);
            System.out.println(aaa.get("aaa"));

        }
    }

    @Test
    public void test3() {

        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get("asdfucke:asdas".getBytes());

            System.out.println(bytes);

        }
    }


}
