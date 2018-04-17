package com.taotao.redis;

import redis.clients.jedis.Jedis;

/**
 * @author liuam
 * @version 2018/4/17 0017 上午 9:33 创建文件
 */
public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("mytest", "hello world");
        String mytest = jedis.get("mytest");
        System.out.println(mytest);
        jedis.close();
    }
}
