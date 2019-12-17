package designpatterns.tools;

import java.util.concurrent.Future;

public abstract class AbstractHandler implements Handler {

    public void receivedRequest(HandlerContext ctx, Request request) {

        if(ctx.handler instanceof AsynHandler){
            Future future = ((AsynHandler) ctx.handler).asynHandle(request);
            ctx.futureCollector.putFuture(ctx.handler.getClass(),future);
        }

        if(ctx.handler instanceof SynHandler){
            ((SynHandler)ctx.handler).synHandle(request);
        }

        ctx.fireReceivedRequest(request);
    }


}
