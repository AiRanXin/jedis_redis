package com.example.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedisDemo {
    public static void main(String[] args) {
       //创建Jedis对象
        Jedis jedis = new Jedis("192.168.10.101",6379);
        jedis.auth("974836yue");
        //操作key
        //添加
        jedis.set("name","lucy");
        //获取
        String name = jedis.get("name");
        System.out.println(name);
        //设置多个key-value
        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1","k2");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for(String key : keys){
            System.out.println(key);
        }
        String value = jedis.ping();
        System.out.println(value);
    }

}
