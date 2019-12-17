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


    /**
     * 处理请求
     */
    public void fireReceivedRequest(Request request) {
        invokeReceivedRequest(next(), request);
    }

    /**
     * 处理请求
     */
    public static void invokeReceivedRequest(HandlerContext ctx, Request request) {
        if (ctx != null) {
            try {
                ctx.handler().receivedRequest(ctx, request);
            } catch (Throwable e) {
                ctx.handler().exceptionCaught(ctx, e);
            }
        }
    }

    /**
     * 处理响应
     * @param request
     * @return
     */
    public Response fireReturndResponse(Request request) {
         invokeReturndResponse(pre(), request);
        return pre().response;
    }

    /**
     * 处理响应
     * @param ctx
     * @param request
     */
    public static Response invokeReturndResponse(HandlerContext ctx, Request request) {
        if (ctx != null) {
            try {
                Response response = ctx.response;
                if(response==null){
                    Handler handler = ctx.handler();
                    if(handler instanceof AsynHandler){
                        Future future = ctx.futureCollector.getFuture(ctx.handler.getClass());
                         ctx.response  = (Response)future.get();
                         if(ctx.response==null){
                             handler.returndResponse(ctx, request);
                         }
                    }else{
                        handler.returndResponse(ctx, request);
                    }
                }else{
                    return response;
                }
            } catch (Throwable e) {
                ctx.handler().exceptionCaught(ctx, e);
            }
        }
        return null;
    }

    private HandlerContext next() {
        return next;
    }

    private HandlerContext pre(){
        return prev;
    }

    private Handler handler() {
        return handler;
    }
}
