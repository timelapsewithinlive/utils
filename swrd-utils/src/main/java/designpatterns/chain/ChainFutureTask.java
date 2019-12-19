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
    protected void set(Object o) {
        listener.listen((Response)o);
        super.set(o);
    }

    @Override
    public Response get(long timeout, TimeUnit unit){
        Response response=null;
        try{
            Object o = super.get(timeout, unit);
            if(o!=null){
                response=(Response) o;
            }else{
                response =new Response(FlagEnum.FAIL,null);
                response.setCause(new ExceptionWithoutTraceStack("异步handler未返回结果或获取结果超时"));
            }
        }catch (Exception e){
            response =new Response(FlagEnum.FAIL,null);
            response.setCause(e);
        }
        return response;
    }
}
