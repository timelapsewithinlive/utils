package jdk.concurrent.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class TestMyLock {
    public  static List<String> list=new ArrayList<>();

    //模板模式
    static Lock writeLock=new WriteLock();

    static  class  Task implements  Runnable{

        @Override
        public void run() {

            for (int i=0;i<100;i++){
               writeLock.lock();
               list.add(Thread.currentThread().getName()+"---"+i);
               writeLock.unlock();
            }
        }
    }
    public static void main(String[] args) throws  Exception{

        List<Thread>list=new ArrayList<>();

        Task task=new Task();
        for (int i=0;i<100;i++){
            list.add(new Thread(task,"thread"+i));
        }
        for (int i=0;i<100;i++){
            list.get(i).start();
        }
        for (int i=0;i<100;i++){
            list.get(i).join();
        }
        System.out.println(TestMyLock.list.size());

    }

}
