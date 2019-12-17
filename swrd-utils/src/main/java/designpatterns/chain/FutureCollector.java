package designpatterns.chain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class FutureCollector {

    private  ConcurrentHashMap<String, Future> handlerMapFuture;

    public FutureCollector(ConcurrentHashMap<String, Future> handlerMapFuture) {
        this.handlerMapFuture = handlerMapFuture;
    }

    public FutureCollector putFuture(Class dependency,Future future){
        if(future!=null){
            handlerMapFuture.put(Thread.currentThread().getId()+"_"+dependency.getSimpleName(),future);
        }
        return this;
    }

    public Future getFuture(Class dependency){
        Future future = handlerMapFuture.get(Thread.currentThread().getId()+"_"+dependency.getSimpleName());
        return future;
    }

    public ConcurrentHashMap<String, Future> getHandlerMapFuture() {
        return handlerMapFuture;
    }

    public void setHandlerMapFuture(ConcurrentHashMap<String, Future> handlerMapFuture) {
        this.handlerMapFuture = handlerMapFuture;
    }
}
