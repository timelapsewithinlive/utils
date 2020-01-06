package jdk.socket.bio.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TimeClient {
    public static void main(String[] args) {
        int port=9090;
        if(args!=null&&args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            } catch (Exception e) {
                // 采用默认值
            }
        }

        Socket socket=null;
        BufferedReader in=null;
        PrintWriter out=null;

        try {
            socket=new Socket("127.0.0.1",port);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner = new Scanner(System.in);
            while(true){
                if(scanner.nextLine().equals("请求")){
                    send( socket, in, out);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            /*if(out!=null){
                out.close();
                out=null;
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in=null;
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket=null;
            }*/
        }
    }

    public static  void send(Socket socket,BufferedReader in,PrintWriter out) throws IOException {
        while(true) {
            out.println("等待消息接收完成");
            String resp = in.readLine();
            if(resp.equals("完成")){
                break;
            }
        }
        System.out.println("业务逻辑开始.....");
    }
}
