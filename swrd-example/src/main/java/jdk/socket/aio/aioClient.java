/**
a@author zuohaoshibuliuming
@date 2017年5月9日---下午6:11:28
@explain:
*/
package jdk.socket.aio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class aioClient {
	public static void main(String[] args) throws InterruptedException, ExecutionException  {
		AsynchronousSocketChannel client =null;;
		try {
			client = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1", 8888);
		@SuppressWarnings("rawtypes")
		Future future = client.connect(hostAddress);
		while (!future.isDone()) {
		     System.out.println("正在连接。。。。");
		}
		try {
			Object object = future.get();
			System.out.println(future.isDone());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		System.out.println("Client is started");
		System.out.println("Sending message to server: ");
		byte [] bytes = new String("start").getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		Future result = client.write(buffer);
		while (! result.isDone()) {
		     System.out.println("... ");
		}
		System.out.println(result.isDone());
		Integer object =(Integer) result.get();
		System.out.println(object);
		buffer.clear();
		final ByteBuffer bb = ByteBuffer.allocate(1024);  
		client.read(bb, null, new CompletionHandler<Integer,Object>(){  
            @Override  
            public void completed(Integer result, Object attachment) {  
                 System.out.println(result);  
                 System.out.println(new String(bb.array()));  
            }  
            @Override  
            public void failed(Throwable exc, Object attachment) {  
                    exc.printStackTrace();  
                }  
            }  
        );  
		//System.out.println(new String(buffer.array()).trim());
		buffer.clear();    
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
