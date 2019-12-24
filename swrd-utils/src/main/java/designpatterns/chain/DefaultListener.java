package designpatterns.chain;

import java.lang.reflect.Method;

public class DefaultListener implements Listener{

    private HandlerContext ctx;

    private Request request;

    public DefaultListener(HandlerContext ctx, Request request) {
        this.ctx = ctx;
        this.request = request;
    }

    @Override
    public void listen(Object obj) {

        if(obj instanceof Response){
            Response response= (Response)obj;
            if(response==null|| HandlerCurrentlyStatus.FAIL.equals(response.getFlag())){
                request.isPropagation.compareAndSet(true,false);
            }else{
                ctx.response=response;
            }
        }

        if(obj instanceof Throwable){
            request.isPropagation.compareAndSet(true,false);
            Response resp = new Response(HandlerCurrentlyStatus.FAIL,null);
            resp.setCause((Throwable)obj);
            ctx.response=resp;
        }

        try{
            Method method = ctx.handler.getClass().getDeclaredMethod(Constants.UN_NECESSARY_METHOD, Request.class);
            UnNecessary annotation = method.getAnnotation(UnNecessary.class);
            if(annotation==null){
                request.countDownLatch.countDown();
                System.out.println(ctx.handler.getClass().getName() +"到达--------"+this.getClass().getName());
            }
        }catch (Exception e){
            request.isPropagation.compareAndSet(true,false);
            Response resp = new Response(HandlerCurrentlyStatus.FAIL,null);
            resp.setCause(e);
            ctx.response=resp;
        }

    }

}
