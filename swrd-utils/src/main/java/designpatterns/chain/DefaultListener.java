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
        ctx.response=response;
        request.isPropagation.compareAndSet(true,false);
    }

}
