package designpatterns.chain;

import exception.ExceptionWithoutTraceStack;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

public abstract class AbstractHandler implements Handler {

    //依赖的其它handler处理结果，从futureCollector通过class的简单类名和线程id进行获取
    public Class[] denpencies;

    @PostConstruct
    public void init(){
        denpencies =null;//具体的值通过枚举值来取，或者配置文件等等。
    }

    @Override
    public void receivedRequest(HandlerContext ctx, Request request) {
        //如果检测到前边的异步handler标识为不继续传播，则直接结束
        if(!request.isPropagation.get()){
            return;
        }

        //如果是异步的，提交到线程池进行处理
        if(ctx.handler instanceof AsynHandler){

            //如果正在执行任务的线程小于cpu核心数的两倍，就提交到线程池。该方法有锁，可以去掉。执行拒绝策略
            if(threadPoolExecutor.getActiveCount()<Config.THREAD_POOL_NUM){
                RequestTask task = new RequestTask(ctx, request);
                Future future = submit(task) ;
                //异步结果进行统一收集
                ctx.futureCollector.putFuture(ctx.handler.getClass(),future);
            }else{
                //如果没可用线程。用当前线程执行任务
                ctx.response = ((AsynHandler) ctx.handler).asynHandle(request);
                //当前线程处理结果判断是否继续传播
                isPropagation(ctx, request);
            }

        }

        //同步handler处理方式
        if(ctx.handler instanceof SynHandler){
             ctx.response= ((SynHandler) ctx.handler).synHandle(request);
             //如果当前handler执行的结果不符合预期成功结果，那么直接改变责任链到尾部。其它后续的节点不执行
            //当前线程处理结果判断是否继续传播
            isPropagation(ctx, request);
        }

        //通过上下文传递，继续进行下一个handler调用
        if(request.isPropagation.get()){
            ctx.fireReceivedRequest(request);
        }
    }

    private void isPropagation(HandlerContext ctx,Request request){
        if(ctx.response==null||FlagEnum.FAIL.equals(ctx.response.getFlag())){
            if(ctx.response==null){
                ctx.response=new Response(FlagEnum.FAIL,null);
                ctx.response.setCause(new RuntimeException(ctx.handler.getClass().getSimpleName() +" 未返回结果，直接结束链路执行"));
            }
            if(ctx.next!=ctx.tail){
                ctx.next=ctx.tail;
                ctx.tail.prev=ctx;
                return;
            }
            request.isPropagation.set(false);
        }
    }

    //响应结果处理，从尾部节点开始向前查找结果
    @Override
    public void returndResponse(HandlerContext ctx, Request request){
            ctx.fireReturndResponse(request);
    }

    //请求和响应异常的统一处理。响应目前只存在future超时的异常，因为响应不参与业务处理
    @Override
    public void exceptionCaught(HandlerContext ctx, Throwable e) {
        //出现异常时，上下文的reponse为null
        if(ctx.response==null){
            ctx.response=new Response(FlagEnum.FAIL,null);
        }
        ctx.response.setCause(e);
    }

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Config.THREAD_POOL_NUM, Config.THREAD_POOL_NUM,Config.THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,new SynchronousQueue(), new RejectedExecutionHandler() {
        //线程池满时用当前线程处理任务
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            /*if(true){
                throw new ExceptionWithoutTraceStack("执行拒绝策略异常");
            }*/
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

        //线程池的线程有异常，直接抛出
        @Override
        public Response call() throws Exception {
            //Thread.sleep(10000);
            Response response = ((AsynHandler)ctx.handler).asynHandle(request);
            ctx.response=response;
            return response;
        }

        @Override
        public String toString() {
            return "RequestTask{" +
                    "ctx=" + ctx +
                    ", request=" + request +
                    '}';
        }
    }

    public Response getHandlerResponse(Class handler,Request request) throws InterruptedException, ExecutionException, TimeoutException, IllegalAccessException, InstantiationException {
        ContextCollector contextCollector = request.getContextCollector();
        HandlerContext context = contextCollector.getContext(handler);
        if(context.response==null){
            if(handler.newInstance() instanceof AsynHandler){
                Future<Response> future = context.futureCollector.getFuture(handler);
                Response resp = future.get(Config.FUTURE_TIME_OUT, TimeUnit.SECONDS);
                if(resp==null){
                    throw new ExceptionWithoutTraceStack(handler.getSimpleName()+ "未返回结果");
                }else{
                    return resp;
                }
            }else{
                throw new ExceptionWithoutTraceStack(handler.getSimpleName()+ "未返回结果");
            }
        }else{
            return context.response;
        }
    }

    public Class[] getDenpencies() {
        return denpencies;
    }

    public void setDenpencies(Class[] denpencies) {
        this.denpencies = denpencies;
    }


}
