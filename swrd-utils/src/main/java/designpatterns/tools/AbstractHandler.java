package designpatterns.tools;

import designpatterns.CommitHandler;

import java.util.concurrent.*;

public abstract class AbstractHandler implements Handler {

    public Handler[] denpencies;

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
        }
        ctx.fireReceivedRequest(request);
    }


    public void returndResponse(HandlerContext ctx, Request request) throws ExecutionException, InterruptedException {
            ctx.fireReturndResponse(request);
    }

    public Future submit(Callable callable){
        Future submit = threadPoolExecutor.submit(callable);
        return submit;
    }

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2, Runtime.getRuntime().availableProcessors()*2,60000, TimeUnit.MILLISECONDS,new SynchronousQueue(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            r.run();
        }
    });

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

    public Handler[] getDenpencies() {
        return denpencies;
    }

    public void setDenpencies(Handler[] denpencies) {
        this.denpencies = denpencies;
    }

    public void init(){
        denpencies =null;//具体的值通过枚举值来取
    }
}
