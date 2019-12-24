package designpatterns.chain;

import exception.ExceptionWithoutTraceStack;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.concurrent.*;

public abstract class AbstractHandler implements Handler {

    //依赖的其它handler处理结果，从futureCollector通过class的简单类名和线程id进行获取
    public Class[] denpencies;

    public int timeOut=Config.FUTURE_TIME_OUT_DEFAULT;

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
            if(CHAIN_THREAD_POOL.getActiveCount()<Config.THREAD_POOL_NUM){

                RequestTask task = new RequestTask(ctx, request);
                ChainFuture future = submit(task,new DefaultListener(ctx, request));

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
             //事物方法开始前，如果前边的异步handler任何一个出现异常。都不向下传播.。
            //现在改为aop实现

            /* if(!isTransactional(ctx, request)){
                 request.isPropagation.set(false);
                 return;
             }*/

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
        if(ctx.response==null) {
            throw new ExceptionWithoutTraceStack(ctx.handler.getClass().getSimpleName() +" 未返回结果，直接结束链路执行");
        }
        if(HandlerCurrentlyStatus.FAIL.equals(ctx.response.getFlag())){
            if(ctx.next!=ctx.tail){
                ctx.next=ctx.tail;
                ctx.tail.prev=ctx;
                return;
            }
            request.isPropagation.set(false);
        }
    }

    @Deprecated
    private boolean isTransactional(HandlerContext ctx,Request request){
        try {
            Method method = ctx.handler.getClass().getDeclaredMethod(Constants.TRANSATIONAL_METHOD, Request.class);
            ChainTransactional annotation = method.getAnnotation(ChainTransactional.class);
            if(annotation!=null){
                request.countDownLatch.await();
                return true;
               // return ctx.futureCollector.isDone();
            }
        } catch (Exception e) {
            Response resp = new Response(HandlerCurrentlyStatus.FAIL,null);
            resp.setCause(e);
            ctx.response=resp;
            return false;
        }
        return true;
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
            ctx.response=new Response(HandlerCurrentlyStatus.FAIL,null);
        }
        ctx.response.setCause(e);
    }

    public static ChainThreadPoolExecutor CHAIN_THREAD_POOL = new ChainThreadPoolExecutor(Config.THREAD_POOL_NUM, Config.THREAD_POOL_NUM,Config.THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,new SynchronousQueue(), new RejectedExecutionHandler() {
        //线程池满时用当前线程处理任务
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            /*if(true){
                throw new ExceptionWithoutTraceStack("执行拒绝策略异常");
            }*/
            r.run();
        }
    });

    public ChainFuture submit(Callable callable,Listener listener){
        ChainFuture submit = CHAIN_THREAD_POOL.submit(callable,listener);
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
            FutureCollector futureCollector = context.futureCollector;
            Future<Response> future = futureCollector.getFuture(handler);
            Response resp = future.get(timeOut, TimeUnit.SECONDS);
            if(resp==null){
                throw new ExceptionWithoutTraceStack(handler.getSimpleName()+ "未返回结果");
            }else{
                return resp;
            }
        }else{
            return context.response;
        }
    }

    public Class[] getDenpencies() {
        return denpencies;
    }

    public abstract void setDenpencies(Class[] denpencies);

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
