package designpatterns.chain;

import java.util.concurrent.ConcurrentHashMap;

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

    public ConcurrentHashMap<String, HandlerContext> getHandlerMapContext() {
        return handlerMapContext;
    }

    public void setHandlerMapContext(ConcurrentHashMap<String, HandlerContext> handlerMapContext) {
        this.handlerMapContext = handlerMapContext;
    }
}
