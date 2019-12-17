package designpatterns.tools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class ContextCollector {

    private ConcurrentHashMap<String, HandlerContext> handlerMapContext;


    public ContextCollector putContext(Class handler,HandlerContext context){
        if(context!=null){
            handlerMapContext.put(Thread.currentThread().getId()+"_"+handler.getSimpleName(),context);
        }
        return this;
    }

    public HandlerContext getContext(Class dependency){
        HandlerContext context = handlerMapContext.get(Thread.currentThread().getId()+"_"+dependency.getSimpleName());
        return context;
    }

    public ContextCollector(ConcurrentHashMap<String, HandlerContext> handlerMapFuture) {
        this.handlerMapContext = handlerMapFuture;
    }

    public ConcurrentHashMap<String, HandlerContext> getHandlerMapFuture() {
        return handlerMapContext;
    }

    public void setHandlerMapFuture(ConcurrentHashMap<String, HandlerContext> handlerMapFuture) {
        this.handlerMapContext = handlerMapFuture;
    }
}
