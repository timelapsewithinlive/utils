package designpatterns.chain;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

public abstract class AbstractHandler implements Handler {

    //依赖的其它handler处理结果，从futureCollector通过class的简单类名和线程id进行获取
    public Class[] denpencies;

    @PostConstruct
    public void init(){
        denpencies =null;//具体的值通过枚举值来取
    }

    @Override
    public void receivedRequest(HandlerContext ctx, Request request) {
        if(ctx.handler instanceof AsynHandler){
            RequestTask task = new RequestTask(ctx, request);
            Future future =null;
            if(threadPoolExecutor.getActiveCount()<Runtime.getRuntime().availableProcessors()){
                 future = submit(task) ;
            }else{
                future = new FutureTask(task);
                ((FutureTask) future).run();
            }
            ctx.futureCollector.putFuture(ctx.handler.getClass(),future);
        }

        if(ctx.handler instanceof SynHandler){
             ctx.response= ((SynHandler) ctx.handler).synHandle(request);
             if(FlagEnum.FAIL.equals(ctx.response.getFlag())){
                 if(ctx.next!=ctx.tail){
                     ctx.next=ctx.tail;
                     ctx.tail.prev=ctx;
                     return;
                 }
             }
        }
        ctx.fireReceivedRequest(request);
    }

    @Override
    public void returndResponse(HandlerContext ctx, Request request){
            ctx.fireReturndResponse(request);
    }

    @Override
    public void exceptionCaught(HandlerContext ctx, Throwable e) {
        try{
            if(ctx.response==null){
                ctx.response=new Response(FlagEnum.FAIL,null);
                ctx.response.setCause(e);
            }
        }catch (Exception exe){
            ctx.response.setFlag(FlagEnum.FAIL);
            ctx.response.setCause(exe);
        }finally {
            if(ctx.next!=ctx.tail){
                ctx.next=ctx.tail;
                ctx.tail.prev=ctx;
            }
        }
    }

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2, Runtime.getRuntime().availableProcessors()*2,60000, TimeUnit.MILLISECONDS,new SynchronousQueue(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            r.run();
        }
    });

    public Future submit(Callable callable){
        Future submit = threadPoolExecutor.submit(callable);
        return submit;
    }

    public static class RequestTask implements Callable{
        private HandlerContext ctx;
        private Request request;

        public RequestTask(HandlerContext ctx, Request request) {
            this.ctx = ctx;
            this.request = request;
        }

        @Override
        public Response call() throws Exception {
            Response response = ((AsynHandler)ctx.handler).asynHandle(request);
            ctx.response=response;
            return response;
        }
    }

    public Class[] getDenpencies() {
        return denpencies;
    }

    public void setDenpencies(Class[] denpencies) {
        this.denpencies = denpencies;
    }


}
