package designpatterns.tools;

public interface Handler {

    /**
     * 处理接收到前端请求的逻辑
     */
    default void receivedRequest(HandlerContext ctx, Request request) {
        ctx.fireReceivedRequest(request);
    }

    /**
     * 当实现的前面的方法抛出异常时，将使用当前方法进行异常处理，这样可以将每个handler的异常
     * 都只在该handler内进行处理，而无需额外进行捕获
     */
    default void exceptionCaught(HandlerContext ctx, Throwable e) {
        throw new RuntimeException(e);
    }

}
