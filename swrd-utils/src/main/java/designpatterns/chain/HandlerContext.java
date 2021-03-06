package designpatterns.chain;

import exception.ExceptionWithoutTraceStack;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class HandlerContext {

    FutureCollector futureCollector;
    HandlerContext head;
    HandlerContext tail;
    HandlerContext prev;
    HandlerContext next;
    Handler handler;
    volatile Response response;
    //CountDownLatch countDownLatch;


    /**
     * 处理请求
     */
    public void fireReceivedRequest(Request request) {
        invokeReceivedRequest(next(), request);
    }

    /**
     * 处理请求
     *
     * 请求时，进行后续节点剔除时，只剔除同步节点。异步节点在响应时如果执行过，则逐一获取检查。
     * handler异常时，不进行后续节点剔除。
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
     * @param request
     * @return
     */
    public void fireReturndResponse(Request request) {
         invokeReturndResponse(pre(), request);
    }

    /**
     * 处理响应
     * @param ctx
     * @param request
     *
     * 响应时从尾部节点开始查找。因为请求handler异常时，不进行后续节点剔除。
     * 查找响应时，要考虑异步handler结果的获取
     */
    public static void invokeReturndResponse(HandlerContext ctx, Request request) {
            try {
                if (ctx.response == null) {
                    Handler handler = ctx.handler();
                    //异步handler的判断逻辑
                    if (handler instanceof AsynHandler) {
                        ChainFutureTask future = (ChainFutureTask)ctx.futureCollector.getFuture(ctx.handler.getClass());
                        //如果未获取到future,代表链路未执行到该异步handler
                        if (future != null) {
                            //超时，抛出异常，进行响应异常处理
                            Response response = future.get(((AbstractHandler)ctx.handler).timeOut);
                            //如果未获取到结果，说明handler没有返回值
                            if (response == null) {
                                throw new ExceptionWithoutTraceStack(handler.getClass().getSimpleName() + " 获取异步任务结果异常,业务侧未进行结果返回");
                            } else {
                                //获取链路最后一次执行的结果，非尾部节点，将值赋值给尾部节点，否则直接返回
                                if (ctx.next != null) {
                                    //获取到结果后，直接赋值给尾部节点。重新整理链路
                                    ctx.next = ctx.tail;
                                    ctx.tail.prev = ctx;
                                    ctx.next.response = response;
                                } else {
                                    return;
                                }
                            }
                        } else {
                            //同步的话继续向前寻找最后一个执行的节点,因为前边执行请求时已经校验了同步handler返回结果不能为空。
                            ctx.handler.returndResponse(ctx, request);
                        }
                    }else if(handler instanceof SynHandler){
                        throw new ExceptionWithoutTraceStack(handler.getClass().getName() + " 获取同步任务结果异常,业务侧未进行结果返回");
                    }else{
                        //这里如果是尾节点时继续向前寻找
                        ctx.handler.returndResponse(ctx, request);
                    }
                }else if(!request.isPropagation.get()&& HandlerCurrentlyStatus.SUCCESS.equals(ctx.response.getFlag())){
                    //如果节点不为空，但是传播标识为false,且节点执行成功。证明前边的节点出现过异常。一直找到出现异常的节点
                    ctx.handler.returndResponse(ctx, request);
                } else{
                    //获取链路最后一次执行的结果，非尾部节点，将值赋值给尾部节点，否则直接返回
                    if(ctx.next!=null){
                        //获取到结果后，直接赋值给尾部节点。重新整理链路
                        ctx.next=ctx.tail;
                        ctx.tail.prev=ctx;
                        ctx.next.response=ctx.response;
                    }else{
                        return;
                    }
                }
            } catch (Throwable e) {
                //future获取response超时，或者response为空会出现异常
              /*  ctx.response=new Response(HandlerCurrentlyStatus.FAIL,null);
                ctx.response.setCause(e);*/
                ctx.handler.exceptionCaught(ctx, e);
                if(ctx.next!=null){//不等于空，证明不是尾节点
                    ctx.next=ctx.tail;
                    ctx.tail.prev=ctx;
                    ctx.next.response=ctx.response;
                }
            }
    }

    /**
     * 如果response中的cause不为空，需要释放库存、事物回滚等等
     * @param request
     */
    public void fireReleaseSource(Request request) {
        invokeReleaseSource(pre(), request);
    }

    /**
     *事物回滚。没有具体实现，有时间再写吧
     * @param ctx
     * @param request
     */
    //TODO
    public static void invokeReleaseSource(HandlerContext ctx, Request request) {
        ctx.handler.releaseSource(ctx, request);
    }

    private HandlerContext next(){
        return next;
    }

    private HandlerContext pre(){
        return prev;
    }

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
}
