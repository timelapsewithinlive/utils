package designpatterns.chan2;

/**
 * 默认处理器实现
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:56 上午
 */
public abstract class AbstractHandler implements Handler, Parser {

    /**
     * 执行请求
     *
     * @param ctx
     * @param request
     */
    @Override
    public void receivedRequest(HandlerContext ctx, Request request) {
        Object data = ctx.handler.parse(request.getContent().toString(), request.getT());
        ctx.response.setData(data);
        ctx.fireReceivedRequest(request);
    }

    /**
     * 获取结果
     *
     * @param ctx
     */
    @Override
    public void returndResponse(HandlerContext ctx) {
        //获取到结果后，直接赋值给尾部节点。重新整理链路
        if (ctx.response.getCause() != null) {
            ctx.tail.response.setCause(ctx.response.getCause());
        } else if (ctx.response.getData() != null) {
            ctx.tail.response.setData(ctx.response.getData());
        } else {
            ctx.fireReturndResponse();
        }
    }

    /**
     * 全局异常处理
     *
     * @param ctx
     * @param e
     */
    @Override
    public void exceptionCaught(HandlerContext ctx, Throwable e) {
        ctx.response.setFlag(CurrentlyStatus.FAIL);
        ctx.response.setCause(e);
    }

}
