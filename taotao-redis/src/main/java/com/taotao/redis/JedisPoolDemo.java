package com.taotao.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author liuam
 * @version 2018/4/17 0017 上午 9:36 创建文件
 */
public class JedisPoolDemo {
    public static void main(String[] args) {
        // 构建连接数配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大连接数
        jedisPoolConfig.setMaxTotal(50);

        // 构建连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
        // 将连接资源还回连接池中
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.get("mytest"));
        // 将连接还回到连接池中
        jedisPool.returnResource(jedis);
        // 释放连接池
        jedisPool.close();
    }
}
