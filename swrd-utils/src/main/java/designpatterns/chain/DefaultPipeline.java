package designpatterns.chain;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Component("pipeline")
@Scope("prototype")//多例是因为有成员变量。每个请求都有自己的上下文
public class DefaultPipeline implements Pipeline, ApplicationContextAware, InitializingBean {

    private ApplicationContext context;

    HandlerContext head;
    HandlerContext tail;

    private Request request;

    private FutureCollector futureCollector; //异步handler结果收集器

    private ContextCollector contextCollector;//一个请求经历过链路的上下文

    //头部节点和尾部节点默认实现。所有handler为单例，因为handler不在成员变量体现。
    // 具体的rquest和respones在多例的handlerContext中传递
    private static final Handler DEFAULT_HANDLER = new Handler() {
        @Override
        public void receivedRequest(HandlerContext ctx, Request request) {
            ctx.fireReceivedRequest(request);
        }

        @Override
        public void returndResponse(HandlerContext ctx, Request request) {
            ctx.fireReturndResponse(request);
        }

        @Override
        public void exceptionCaught(HandlerContext ctx, Throwable e) {
        }
    };

    //请求进来时进行一些初始化
    public void afterPropertiesSet() throws Exception {
        head = newContext(DEFAULT_HANDLER);
        tail = newContext(DEFAULT_HANDLER);
        head.next = tail;
        tail.prev = head;

        futureCollector=new FutureCollector(new ConcurrentHashMap<String, Future>());
        contextCollector=new ContextCollector(new ConcurrentHashMap<String, HandlerContext>());
        request.setContextCollector(contextCollector);


    }

    public DefaultPipeline() {
    }

    public DefaultPipeline(Request request) {
        this.request = request;
    }

    //请求开始的入口
    public Pipeline fireReceiveRequest() {
        HandlerContext.invokeReceivedRequest(head, request);
        return this;
    }

    //获取响应的入口
    public Pipeline fireReturnResponse() {
        HandlerContext.invokeReturndResponse(tail, request);
        return this;
    }

    //以后再实现
    @Override
    public Pipeline fireReleaseSource() {
        return null;
    }

    //添加handler到链表中
    public Pipeline addLast(Handler handler) {
        HandlerContext handlerContext = newContext(handler);
        tail.prev.next = handlerContext;
        handlerContext.prev = tail.prev;
        handlerContext.next = tail;
        tail.prev = handlerContext;

        handlerContext.setHead(head);
        handlerContext.setTail(tail);

        contextCollector.putContext(handler.getClass(),handlerContext);
        return this;
    }

    private HandlerContext newContext(Handler handler) {
        HandlerContext context = this.context.getBean(HandlerContext.class);
        context.handler = handler;
        context.futureCollector=futureCollector;
        return context;
    }

    public Response response(){
        return tail.response;
    }

    public boolean isDone(){
        return false;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
