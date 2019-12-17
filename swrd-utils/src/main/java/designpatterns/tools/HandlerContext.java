package designpatterns.tools;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class HandlerContext {

    FutureCollector futureCollector;
    HandlerContext prev;
    HandlerContext next;
    Handler handler;
    volatile Response response;


    public Response fireReceivedRequest(Request request) {
       return invokeReceivedRequest(next(), request);
    }

    /**
     * 处理接收到任务的事件
     */
    public Response invokeReceivedRequest(HandlerContext ctx, Request request) {
        if (ctx != null) {
            try {
                ctx.handler().receivedRequest(ctx, request);
            } catch (Throwable e) {
                ctx.handler().exceptionCaught(ctx, e);
            }
        }
        if(response==null){
            Future future = futureCollector.getFuture(handler.getClass());
            try {
                future.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                response = new Response();
                e.printStackTrace();
            }
        }
        return response;
    }

    private HandlerContext next() {
        return next;
    }

    private Handler handler() {
        return handler;
    }
}
