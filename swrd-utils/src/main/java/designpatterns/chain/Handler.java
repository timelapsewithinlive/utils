package designpatterns.chain;

import java.util.concurrent.ExecutionException;

public interface Handler {


    default void receivedRequest(HandlerContext ctx, Request request) {
        ctx.fireReceivedRequest(request);
    }

    default void returndResponse(HandlerContext ctx, Request request) throws ExecutionException, InterruptedException {
            ctx.fireReturndResponse(request);
    }


    default void exceptionCaught(HandlerContext ctx, Throwable e) {
        throw new RuntimeException(e);
    }

}
