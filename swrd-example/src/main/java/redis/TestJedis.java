package redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestJedis {
    static  Jedis jedis = new Jedis("192.168.99.100", 6379);

    public static void main(String[] args) throws IOException {
      /*  jedis.set("test","哈哈哈");
        System.out.println(jedis.get("test"));*/

       String script="local value = ARGV[1]\r\n"+
                "for k, v in ipairs(KEYS) do\r\n"+
                "redis.call('set', v,value)\r\n"+
                "end";

        //String script =RedisUtils.readToString("E:\\work\\code\\secoo-github\\utils\\swrd-example\\src\\main\\java\\redis\\update_ticket_batch.lua");
        System.out.println(script);

        List<String> keys = new ArrayList<>();
        keys.add("swrd");
        List<String> params = new ArrayList<>();
        params.add("value");

        jedis.eval(script,keys,params);
        System.out.println(jedis.get("swrd"));

    }

    public static void 令牌桶限流(){
    }

}
