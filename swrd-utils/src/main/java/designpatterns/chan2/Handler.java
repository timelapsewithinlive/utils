package designpatterns.chan2;

/**
 * 责任链业务处理器
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:23 上午
 */
public interface Handler<T> extends Parser {

    /**
     * 接受处理请求，该方法的主要逻辑在abstract。从头节点开始处理
     *
     * @param ctx
     * @param request
     */
    void receivedRequest(HandlerContext ctx, Request request);

    /**
     * 处理响应，从尾节点开始逐步寻找Response
     *
     * @param ctx
     */
    void returndResponse(HandlerContext ctx);

    /**
     * handler的业务异常和系统异常统一处理
     *
     * @param ctx
     * @param e
     */
    void exceptionCaught(HandlerContext ctx, Throwable e);

}
