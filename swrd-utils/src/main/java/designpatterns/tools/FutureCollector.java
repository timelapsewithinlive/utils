package designpatterns.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class FutureCollector {

    private ConcurrentHashMap<Class, Future> handlerMapFuture;

    public FutureCollector(ConcurrentHashMap<Class, Future> handlerMapFuture) {
        this.handlerMapFuture = handlerMapFuture;
    }

    public ConcurrentHashMap<Class, Future> getHandlerMapFuture() {
        return handlerMapFuture;
    }

    public void setHandlerMapFuture(ConcurrentHashMap<Class, Future> handlerMapFuture) {
        this.handlerMapFuture = handlerMapFuture;
    }
}
