package designpatterns.chan2;

/**
 * 责任链节点上下文
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 12:30 下午
 */
public class HandlerContext {

    /**
     * 头节点
     */
    HandlerContext head;

    /**
     * 尾节点
     */
    HandlerContext tail;

    /**
     * 前一节点
     */
    HandlerContext prev;

    /**
     * 下一节点
     */
    HandlerContext next;

    /**
     * 节点处理器
     */
    Handler handler;

    /**
     * 响应封装体
     */
    volatile Response response;

    public HandlerContext(Handler handler, Response response) {
        this.handler = handler;
        this.response = response;
    }

    /**
     * 处理请求
     *
     * @param request
     */
    public void fireReceivedRequest(Request request) {
        invokeReceivedRequest(next(), request);
    }

    /**
     * 处理请求
     *
     * @param ctx     当前节点上下文
     * @param request
     */
    public static void invokeReceivedRequest(HandlerContext ctx, Request request) {
        try {
            ctx.handler().receivedRequest(ctx, request);
        } catch (Throwable e) {
            ctx.handler().exceptionCaught(ctx, e);
        }
    }

    /**
     * 处理响应
     *
     * @return
     */
    public void fireReturndResponse() {
        invokeReturndResponse(pre());
    }

    /**
     * 处理响应
     *
     * @param ctx
     */
    public static void invokeReturndResponse(HandlerContext ctx) {
        try {
            ctx.handler.returndResponse(ctx);
        } catch (Throwable e) {
            ctx.handler.exceptionCaught(ctx, e);
        }
    }

    /**
     * 获取当前节点的下一节点
     *
     * @return
     */
    private HandlerContext next() {
        return next;
    }

    /**
     * 获取当前节点的上一节点
     *
     * @return
     */
    private HandlerContext pre() {
        return prev;
    }

    /**
     * 获取当前节点的处理器
     *
     * @return
     */
    private Handler handler() {
        return handler;
    }

    public HandlerContext getHead() {
        return head;
    }

    public void setHead(HandlerContext head) {
        this.head = head;
    }

    public HandlerContext getTail() {
        return tail;
    }

    public void setTail(HandlerContext tail) {
        this.tail = tail;
    }

    public HandlerContext getPrev() {
        return prev;
    }

    public void setPrev(HandlerContext prev) {
        this.prev = prev;
    }

    public HandlerContext getNext() {
        return next;
    }

    public void setNext(HandlerContext next) {
        this.next = next;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
