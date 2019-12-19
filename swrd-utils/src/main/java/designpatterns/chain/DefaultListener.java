package designpatterns.chain;

import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultListener implements Listener<Response>{

    private HandlerContext ctx;

    private Request request;

    public DefaultListener(HandlerContext ctx, Request request) {
        this.ctx = ctx;
        this.request = request;
    }

    @Override
    public void listen(Response response) {
        if(response==null||FlagEnum.FAIL.equals(response.getFlag())){
            request.isPropagation.compareAndSet(true,false);
        }else{
            ctx.response=response;
        }
    }

}
