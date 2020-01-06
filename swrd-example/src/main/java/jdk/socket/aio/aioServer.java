/**
@author zuohaoshibuliuming
@date 2017年5月9日---下午4:52:18
@explain:
*/
package jdk.socket.bio.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class aioServer {
	static AsynchronousChannelGroup group=null;
	public static void main(String[] args) throws IOException {
		 group  = AsynchronousChannelGroup.withFixedThreadPool(6, Executors.defaultThreadFactory());
		final  AsynchronousServerSocketChannel   listener = AsynchronousServerSocketChannel.open(group);
		 InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1",  8888);
		 listener.bind(hostAddress);
		 final String att1 = "First connection";
		Thread currentThread = Thread.currentThread();
		 listener.accept(att1, new CompletionHandler<AsynchronousSocketChannel, Object>() {
			 @Override
		     public void completed(AsynchronousSocketChannel  ch, Object att) {
		         System.out.println("Completed: " + att);
				 String msg = handleConnection(ch);
		         att = "next completed";    
		         listener.accept(att, this);
		         System.out.println(Thread.currentThread().getName());
		     }    
		     @Override
		     public void failed(Throwable e, Object att) {        
		         System.out.println(att + " - handler failed");
		         e.printStackTrace();
		      }
		 });
	}
	
	private final static String handleConnection(final AsynchronousSocketChannel ch) {
        final ByteBuffer buffer = ByteBuffer.allocate(32);
        ch.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (result > 0) {
                    attachment.flip();
                    String msg = new String(attachment.array()).trim();
                    System.out.println("Message from client: " + msg);
                    attachment.clear();
                    if (msg.equals("close")) {
                        if (!group.isTerminated()) {
                            System.out.println("Terminating the group...");
                            try {
                                group.shutdownNow();
                                group.awaitTermination(10, TimeUnit.SECONDS);
                            } catch (IOException | InterruptedException e) {
                                System.out.println("Exception during group termination");
                                e.printStackTrace();
                            }
                        }
                    }else{
                    	 byte [] bytes = new String("SB").getBytes();
                         ch.write(buffer.wrap(bytes));
                         buffer.flip();
                    }
                } 
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(" - handler failed");
                exc.printStackTrace();
               // currentThread.interupt();
            }
        });
        return "";
    }
}
