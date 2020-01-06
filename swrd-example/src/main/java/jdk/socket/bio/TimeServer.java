package jdk.socket.bio.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
    public static void main(String[] args) throws Exception {
        int port=9090;
        if(args!=null&&args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            } catch (Exception e) {
                // 采用默认值
            }
        }

        ServerSocket server=null;

        try {
            server=new ServerSocket(port);
            System.out.println("The time server is start in port:"+port);
            Socket socket=null;
            while(true){//通过一个无限循环来监听客户端的连接
                socket=server.accept();//如果没有客户端接入，则主线程阻塞在ServerSocket的accept操作上。
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(server!=null){
                System.out.println("The time server close");
                try {
                    server.close();
                    server=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
