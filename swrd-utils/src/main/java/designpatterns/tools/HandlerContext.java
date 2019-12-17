package designpatterns.tools;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class HandlerContext {

    FutureCollector futureCollector;
    HandlerContext prev;
    HandlerContext next;
    Handler handler;


    public void fireReceivedRequest(Request request) {
        invokeReceivedRequest(next(), request);
    }

    /**
     * 处理接收到任务的事件
     */
    static void invokeReceivedRequest(HandlerContext ctx, Request request) {
        if (ctx != null) {
            try {
                ctx.handler().receivedRequest(ctx, request);
            } catch (Throwable e) {
                ctx.handler().exceptionCaught(ctx, e);
            }
        }
    }

    private HandlerContext next() {
        return next;
    }

    private Handler handler() {
        return handler;
    }
}
