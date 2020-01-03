package redis;

import redis.clients.jedis.Jedis;

public class TestJedis {

    public static void main(String[] args){
        Jedis jedis = new Jedis("192.168.155.130", 6379);
    }

}
