package jdk.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioCopyFile {
    String msg;
    /*这样做的好处是，我们在读取磁盘文件时，再也不用通过内核缓冲区到用户进程缓冲区的来回拷贝操作了。
    操作系统会通过一些页面调度算法来将磁盘文件载入对分页区进行高速缓存的物理内存。
    我们就可以通过映射后物理内存来读取磁盘文件了。*/
    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        //nioCopy2();//272 15 18
        //nioCopy();//23 24
        //traditionalCopy();//86  111 85
        long t2 = System.currentTimeMillis();
        //System.out.println(t2-t1);

        position();
    }

    /**
     * NIO文件内存映射及文件通道
     * @throws IOException
     */
    public static void  nioCopy2() throws IOException{
        File source = new File("E:\\a\\大于2M.jpg");
        File dest = new File("E:\\b\\a.png");
        if(!dest.exists()) {
            dest.createNewFile();
        }
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);
        FileChannel sourceCh = fis.getChannel();
        FileChannel destCh = fos.getChannel();
        MappedByteBuffer mbb = sourceCh.map(FileChannel.MapMode.READ_ONLY, 0, sourceCh.size());
        destCh.write(mbb);
        sourceCh.close();
        destCh.close();
    }

    /**
     * NIO文件通道
     * @throws IOException
     */
    private  static  void nioCopy() throws Exception{
        File source = new File("E:\\a\\大于2M.jpg");
        File dest = new File("E:\\b\\a.png");
        if(!dest.exists()) {
            dest.createNewFile();
        }

        FileInputStream fis = new FileInputStream(source);

        FileOutputStream fos = new FileOutputStream(dest);

        FileChannel sourceCh = fis.getChannel();
        FileChannel destCh = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int read = sourceCh.read(buffer);
        while(read!=-1) {
            buffer.flip();
            //clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据.
            //任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
            buffer.clear();
        }
        destCh.write(buffer);
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        sourceCh.close();

        destCh.close();
        fis.close();
        fos.flush();
        fos.close();
    }

    private  static  void traditionalCopy() throws Exception{

        File source = new File("E:\\b\\png 大于2M.png");
        File dest = new File("E:\\a\\a.png");

        if(!dest.exists()) {

            dest.createNewFile();

        }

        FileInputStream fis = new FileInputStream(source);

        FileOutputStream fos = new FileOutputStream(dest);

        byte [] buf = new byte [1024];

        int len = 0;

        while((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }

        fis.close();

        fos.close();

    }


    private  static  void position() throws Exception{
        File source = new File("E:\\a.txt");
        File dest = new File("E:\\b.txt");
        if(!dest.exists()) {
            dest.createNewFile();
        }

        FileInputStream fis = new FileInputStream(source);

        FileOutputStream fos = new FileOutputStream(dest,true);

        FileChannel sourceCh = fis.getChannel();
        FileChannel destCh = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int read = sourceCh.read(buffer);


        //while(read!=-1) {
            buffer.flip();
            //clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据.
            //任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
            //buffer.clear();
        //}
        destCh.write(buffer);
        buffer.compact();
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        int position = buffer.position();
        long sourceChPosition = sourceCh.position();
        long destChPosition = destCh.position();
        System.out.println("ByteBuffer bufferPosition: "+ position);
        System.out.println("FileChannel sourceChPosition: "+ sourceChPosition);
        System.out.println("FileChannel destChPosition: "+ destChPosition);

        sourceCh.close();
        destCh.close();
        fos.flush();
        fis.close();
        fos.close();

    }
}
