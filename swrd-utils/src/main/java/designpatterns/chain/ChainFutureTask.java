package designpatterns.chain;

import exception.ExceptionWithoutTraceStack;

import java.util.concurrent.*;

public class ChainFutureTask extends FutureTask implements ChainFuture<Response>{
    private Listener listener;

    public ChainFutureTask(Callable callable,Listener listener) {
        super(callable);
        this.listener=listener;
    }

    public ChainFutureTask(Runnable runnable, Object result) {
        super(runnable, result);
    }

    @Override
    public void addListener(Listener listener) {
        setListener(listener);
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void setException(Throwable t){
        listener.listen(t);
        super.setException(t);
    }

    @Override
    protected void set(Object o) {
        listener.listen(o);
        super.set(o);
    }

    public Response get(long timeout){

        Response response=null;
        try{
            Object o=null;
            if(Config.FUTURE_TIME_OUT_NEVER==timeout){
                o=super.get();
            }else if(Config.FUTURE_TIME_OUT_DEFAULT==timeout){
                o= super.get(1, TimeUnit.NANOSECONDS);
            }else{
                o= super.get(timeout, TimeUnit.SECONDS);
            }
            if(o!=null){
                response=(Response) o;
            }else{
                response =new Response(HandlerCurrentlyStatus.FAIL,null);
                response.setCause(new ExceptionWithoutTraceStack("异步handler未返回结果或获取结果超时"));
            }
        }catch (Exception e){
            response =new Response(HandlerCurrentlyStatus.FAIL,null);
            response.setCause(e);
        }
        return response;
    }
}
