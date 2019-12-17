package designpatterns.tools;

public abstract class AbstractHandler implements Handler {

    public void receivedRequest(HandlerContext ctx, Request request) {

        if(ctx.handler instanceof AsynHandler){
            ((AsynHandler)ctx.handler).asynHandle(request);
        }

        if(ctx.handler instanceof SynHandler){
            ((SynHandler)ctx.handler).synHandle(request);
        }

        ctx.fireReceivedRequest(request);
    }


}
