package jdk.memory;

public class TestStack {
    public static void main(String[] args){
        System.out.print("虚拟机内存总量:");
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.print("虚拟机空闲内存量:");
        System.out.println(Runtime.getRuntime().freeMemory());//freeMemory()获取java虚拟机中的空闲内存量

        byte[] aaa = new byte[3*1024*1024];
        System.out.println(aaa);
       /* Byte[] bbb = new Byte[1024*1024];
        System.out.println(bbb);*/

        /* for (int i=0;i<16;++i){
            byte[] aaa = new byte[1024*1024];
            System.out.println(aaa);

            Byte[] bbb = new Byte[1024*1024];
            System.out.println(bbb);
           Thread thread = new Thread();
            thread.start();
        }*/

        System.out.print("虚拟机内存总量:");
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.print("虚拟机空闲内存量:");
        System.out.println(Runtime.getRuntime().freeMemory());//freeMemory()获取java虚拟机中的空闲内存量
    }
}
