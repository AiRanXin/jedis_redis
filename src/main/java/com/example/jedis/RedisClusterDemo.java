package com.example.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import java.lang.String;

public class RedisClusterDemo {
    public static void main(String[] args) {
        HostAndPort hostAndPort = new HostAndPort("192.168.10.101",6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);
        jedisCluster.set("b1","value");
        String value = jedisCluster.get("b1");
        System.out.println("value:"+value);
        jedisCluster.close();
    }
}