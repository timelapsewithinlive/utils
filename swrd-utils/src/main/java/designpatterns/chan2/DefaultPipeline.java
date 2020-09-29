package designpatterns.chan2;

import lombok.Data;

/**
 * 默认责任链管理器实现
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:14 上午
 */
@Data
public class DefaultPipeline implements Pipeline {

    /**
     * 头节点
     */
    HandlerContext head;

    /**
     * 尾节点
     */
    HandlerContext tail;

    /**
     * 请求封装体
     */
    private Request request;

    /**
     * 头尾节点默认处理器实现
     */
    private static final Handler DEFAULT_HANDLER = new Handler() {};

    /**
     * 构造器中初始化基链
     *
     * @param request
     */
    public DefaultPipeline(Request request) {
        this.request = request;
        head = newContext(DEFAULT_HANDLER);
        tail = newContext(DEFAULT_HANDLER);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 请求开始的入口
     *
     * @return
     */
    @Override
    public Pipeline fireReceiveRequest() {
       HandlerContext.invokeReceivedRequest(head, request);
        return this;
    }

    /**
     * 获取响应的入口
     *
     * @return
     */
    @Override
    public Pipeline fireReturnResponse() {
        HandlerContext.invokeReturndResponse(tail);
        return this;
    }

    /**
     * 向链表尾部添加处理器
     *
     * @param handler
     * @return
     */
    @Override
    public Pipeline addLast(Handler handler) {
        HandlerContext handlerContext = newContext(handler);
        tail.prev.next = handlerContext;
        handlerContext.prev = tail.prev;
        handlerContext.next = tail;
        tail.prev = handlerContext;

        handlerContext.setHead(head);
        handlerContext.setTail(tail);
        return this;
    }

    private HandlerContext newContext(Handler handler) {
        HandlerContext context = new HandlerContext(handler,new Response());
        return context;
    }

    public Response response() {
        return tail.response;
    }
}
