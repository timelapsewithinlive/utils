package http;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws Exception {
        //String fsdasdfa="{\"db\":426,\"sql\":\"select * from t_user_shipping where id>=4266000 and id <=4266050\",\"limit\":50}";
        //System.out.println(fsdasdfa);
       /* String sql="{\"db\":426,\"sql\":\"select * from t_user_shipping where" +
                " id>=" +4266000+
                " and id <=" +(4266000+50)+
                "\",\"limit\":50}";*/

        //System.out.println(sql);

        int id =55000;

       while(id>=30000){
            id-=3000;

         /*  String sql ="{\"limit\":\"1\",\"db\":\"426\",\"sql\":\"select * from t_user_shipping where id=" +id+
                   "\",\"limit\":50}";*/

           String sql="{\"db\":426,\"sql\":\"select * from t_user_shipping where" +
                   " id>=" +id+
                   " and id <=" +(id+3000)+
                   "\",\"limit\":3000}";
            String post = HttpUtils.post("http://cloud.siku.cn/api/v1/mdb/queryjobs/",sql , "utf-8");
            //System.out.println(post);


            // FileOutputStream fos = new FileOutputStream(dest);
            FileWriter fos = new FileWriter("E:\\3wan-100wan.txt",true);

            //fos.write(post.getBytes());
            fos.append("\r\n"+post);
            fos.flush();
            fos.close();
           Random random = new Random();
           int ms = random.nextInt(3000);
           System.out.println("id========="+id+", ms-------------------:"+ms);
           Thread.sleep(ms);
        }

    }
}
