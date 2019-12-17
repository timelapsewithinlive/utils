package designpatterns.tools;

import java.util.concurrent.ExecutionException;

public interface Handler {


    default void receivedRequest(HandlerContext ctx, Request request) {
        ctx.fireReceivedRequest(request);
    }

    default Response returndResponse(HandlerContext ctx, Request request) throws ExecutionException, InterruptedException {
          return  ctx.fireReturndResponse(request);
    }


    default void exceptionCaught(HandlerContext ctx, Throwable e) {
        throw new RuntimeException(e);
    }

}
