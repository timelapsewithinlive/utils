package jdk.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TimeServerHandler implements Runnable{
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out=new PrintWriter(this.socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            String body=null;
            String result=null;
            while(true){
                body=in.readLine();
                if(body.equals("请求完成")){
                    result="响应完成";
                }
                out.println(result);
                System.out.println("The time server receive order:"+body);
                //如果请求消息为查询时间的指令"QUERY TIME ORDER"则获取当前最新的系统时间。
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                out.close();
                out=null;
            }
            if(this.socket!=null){
                try {
                    this.socket.close();
                    this.socket=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static  void send(Socket socket,BufferedReader in,PrintWriter out) throws IOException {

    }
}
