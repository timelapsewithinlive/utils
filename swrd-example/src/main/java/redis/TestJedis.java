package redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestJedis {
    static  Jedis jedis = new Jedis("test01-rediscluster.secoolocal.com", 7001);

    public static void main(String[] args) throws IOException {
      /*  jedis.set("test","哈哈哈");
        System.out.println(jedis.get("test"));

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
        System.out.println(jedis.get("swrd"));*/
        String s = "\"{\\\"rows\\\": " +
                "[[40000, 12000159034, \\\"\\\\n\\\\n\\\\n\\\\n\\\\u5218\\\\u4e39 \\\\u5929\\\\u732b3\\\", " +
                "\\\"\\\\u5317\\\\u4eac \\\", \\\"\\\\u76f4\\\\u8f96\\\\u5e02\\\", \\\"\\\\u6d77\\\\u6dc0\\\\u533a \\\", 0, " +
                "\\\"\\\\u897f\\\\u5317\\\\u65fa\\\\u4fdd\\\\u5229\\\\u897f\\\\u5c71\\\\u6797\\\\u8bed\\\\u4e09\\" +
                "\\u533a9\\\\u53f7\\\\u697c1\\\\u5355\\\\u5143301\\\\u5ba4\\\", \\\"\\\", \\\"15201623200\\\", null, null, null, 0, " +
                "1, \\\"2014-06-26 16:12:52\\\", \\\"pauca0804\\\", \\\"2019-10-16 10:30:07\\\", \\\"DATA_CLEANING_SYS\\\", 1419, 0, null, " +
                "null, null, 3]], \\\"effect_row\\\": 1, \\\"column_list\\\": [\\\"id\\\", \\\"user_id\\\", \\\"receiver\\\"," +
                " \\\"province\\\", \\\"city\\\", \\\"area\\\", \\\"remote_status\\\", \\\"address\\\", \\\"phone\\\", \\\"mobile\\\"" +
                "" +
                ", \\\"email\\\", \\\"zipcode\\\", \\\"relate_zipcode\\\", \\\"default_address\\\", \\\"is_enable\\\", \\\"create_da" +
                "te\\\", \\\"creator\\\", \\\"modify_date\\\", \\\"modify\\\", \\\"version\\\", \\\"is_pickup\\\", \\\"card_no\\\", \\\"" +
                "front_pic\\\", \\\"back_pic\\\", \\\"perfect_state\\\"]}\"";

        System.out.println(decodeUnicode(s));

    }
    public static String decodeUnicode(String str) {
        Charset set = Charset.forName("UTF-16");
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher m = p.matcher(str);
        int start = 0;
        int start2 = 0;
        StringBuffer sb = new StringBuffer();
        while (m.find(start)) {
            start2 = m.start();
            if (start2 > start) {
                String seg = str.substring(start, start2);
                sb.append(seg);
            }
            String code = m.group(1);
            int i = Integer.valueOf(code, 16);
            byte[] bb = new byte[4];
            bb[0] = (byte) ((i >> 8) & 0xFF);
            bb[1] = (byte) (i & 0xFF);
            ByteBuffer b = ByteBuffer.wrap(bb);
            sb.append(String.valueOf(set.decode(b)).trim());
            start = m.end();
        }
        start2 = str.length();
        if (start2 > start) {
            String seg = str.substring(start, start2);
            sb.append(seg);
        }
        return sb.toString();

    }
    public static void 令牌桶限流(){

    }

}
