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
        if(ctx.response==null){
            ctx.response=new Response(FlagEnum.FAIL,null);
            ctx.response.setCause(e);
            ctx.next=ctx.tail;
            ctx.tail.prev=ctx;
        }
       // throw new RuntimeException(e);
    }

}
