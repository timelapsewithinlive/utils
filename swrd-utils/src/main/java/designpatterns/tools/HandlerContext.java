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
    HandlerContext head;
    HandlerContext tail;
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
    public void fireReturndResponse(Request request) {
         invokeReturndResponse(pre(), request);
    }

    /**
     * 处理响应
     * @param ctx
     * @param request
     */
    public static void invokeReturndResponse(HandlerContext ctx, Request request) {
        if (ctx != null) {
            try {
                if(ctx.response==null){
                    Handler handler = ctx.handler();
                    if(handler instanceof AsynHandler){
                        Future future = ctx.futureCollector.getFuture(ctx.handler.getClass());
                         Response response  = (Response)future.get(10,TimeUnit.SECONDS);
                         if(response==null){
                            throw new RuntimeException("获取异步任务结果异常");
                         }else{
                             if(ctx.next!=null){
                                 ctx.next.response=response;
                             }else{
                                 return;
                             }
                         }
                    }else{
                        ctx.handler.returndResponse(ctx,request);
                    }
                }else{
                    if(ctx.next!=null){
                        ctx.next.response=ctx.response;
                    }else{
                        return;
                    }
                }
            } catch (Throwable e) {
                ctx.handler().exceptionCaught(ctx, e);
            }
        }
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

    public HandlerContext getHead() {
        return head;
    }

    public void setHead(HandlerContext head) {
        this.head = head;
    }

    public HandlerContext getTail() {
        return tail;
    }

    public void setTail(HandlerContext tail) {
        this.tail = tail;
    }
}
