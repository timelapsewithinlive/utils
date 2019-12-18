package designpatterns.chain;

import java.util.concurrent.ExecutionException;

public interface Handler {

     //接受处理请求，该方法的主要逻辑在abstract。从头节点开始处理
     void receivedRequest(HandlerContext ctx, Request request);

     //处理响应，从尾节点开始逐步寻找Response
     void returndResponse(HandlerContext ctx, Request request);

     //response的cause不为空，进行资源回滚。以后有时间再实现
     default void releaseSource(HandlerContext ctx, Request request){
          ctx.fireReleaseSource(request);
     }

     //handler的业务异常和系统异常统一处理
     void exceptionCaught(HandlerContext ctx, Throwable e);


}
