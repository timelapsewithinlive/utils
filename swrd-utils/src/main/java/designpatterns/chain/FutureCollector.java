package designpatterns.chain;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class FutureCollector {

    private  ConcurrentHashMap<String, Future> handlerMapFuture;

    public FutureCollector(ConcurrentHashMap<String, Future> handlerMapFuture) {
        this.handlerMapFuture = handlerMapFuture;
    }

    public FutureCollector putFuture(Class dependency,Future future){
        if(future!=null){
            handlerMapFuture.put(Thread.currentThread().getId()+Constants.SEPARATOR+dependency.getSimpleName(),future);
        }
        return this;
    }

    public Future getFuture(Class dependency){
        Future future = handlerMapFuture.get(Thread.currentThread().getId()+Constants.SEPARATOR+dependency.getSimpleName());
        return future;
    }

    //判断所有的异步handler是否都成功执行
   public boolean isDone(){
        if(handlerMapFuture==null||handlerMapFuture.size()<=0){
            return true;
        }
        boolean isDone=true;
        Set<Map.Entry<String, Future>> entries = handlerMapFuture.entrySet();
        //在事物方法开始前。任何一个异步handler，出现失败，就不往下传播
        //因为hashMap是无序的，必须要遍历所有，查找是否有异常的节点
        for (Map.Entry<String, Future> entry:entries){
            Response response = ((ChainFutureTask) entry.getValue()).get(Config.FUTURE_TIME_OUT_DEFAULT);
            //true代表执行成功，继续向下传播
            if(HandlerCurrentlyStatus.SUCCESS.equals(response.getFlag())){
                continue;
            }else{
                isDone =false;
            }
        }
        return isDone;
    }

    public ConcurrentHashMap<String, Future> getHandlerMapFuture() {
        return handlerMapFuture;
    }

    public void setHandlerMapFuture(ConcurrentHashMap<String, Future> handlerMapFuture) {
        this.handlerMapFuture = handlerMapFuture;
    }
}
