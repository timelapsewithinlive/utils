package designpatterns.tools;

import designpatterns.CommitHandler;

import java.util.concurrent.*;

public abstract class AbstractHandler implements Handler {

    public Handler[] denpencies;

    public void receivedRequest(HandlerContext ctx, Request request) {
        Future future =null;
        if(ctx.handler instanceof AsynHandler){
            Task task = new Task((AsynHandler) ctx.handler, request);
            if(threadPoolExecutor.getActiveCount()<Runtime.getRuntime().availableProcessors()){
                 future = submit(task) ;
            }else{
                ((AsynHandler) ctx.handler).asynHandle(request);
                future = new FutureTask(task);
                ((FutureTask) future).run();
            }
            ctx.futureCollector.putFuture(ctx.handler.getClass(),future);
        }

        if(ctx.handler instanceof SynHandler){
            ((SynHandler)ctx.handler).synHandle(request);
        }

        ctx.fireReceivedRequest(request);
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

    public static class Task implements Callable{
        private AsynHandler handler;
        private Request request;

        public Task(AsynHandler handler, Request request) {
            this.handler = handler;
            this.request = request;
        }

        @Override
        public Object call() throws Exception {
            handler.asynHandle(request);
            return null;
        }
    }

    public Handler[] getDenpencies() {
        return denpencies;
    }

    public void setDenpencies(Handler[] denpencies) {
        this.denpencies = denpencies;
    }
}
