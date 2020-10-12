package designpatterns.chan2;

/**
 * 责任链业务处理器
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:23 上午
 */
public interface Handler<T> extends Bussiness {

    /**
     * 接受处理请求，该方法的主要逻辑在abstract。从头节点开始处理
     *
     * @param ctx
     * @param request
     */
    default void receivedRequest(HandlerContext ctx, Request request){
        Object data = ctx.handler().doBussiness(request.getContent().toString(), request.getT());
        ctx.response.setData(data);
        ctx.fireReceivedRequest(request);
    };

    /**
     * 处理响应，从尾节点开始逐步寻找Response
     *
     * @param ctx
     */
    default void returndResponse(HandlerContext ctx){
        //获取到结果后，直接赋值给尾部节点。重新整理链路
        if (ctx.response.getCause() != null) {
            ctx.tail.response.setCause(ctx.response.getCause());
        } else if (ctx.response.getData() != null) {
            ctx.tail.response.setData(ctx.response.getData());
        } else {
            ctx.fireReturndResponse();
        }
    };

    /**
     * handler的业务异常和系统异常统一处理
     *
     * @param ctx
     * @param e
     */
    default void exceptionCaught(HandlerContext ctx, Throwable e){
        ctx.response.setFlag(CurrentlyStatus.FAIL);
        ctx.response.setCause(e);
    };

}
