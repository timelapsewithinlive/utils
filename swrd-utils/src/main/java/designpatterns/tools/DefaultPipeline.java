package designpatterns.tools;

import designpatterns.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component("pipeline")
@Scope("prototype")
public class DefaultPipeline implements Pipeline, ApplicationContextAware, InitializingBean {

    private FutureCollector futureCollector;

    private static final Handler DEFAULT_HANDLER = new Handler() {};

    private ApplicationContext context;

    private HandlerContext head;
    private HandlerContext tail;

    private Request request;

    public DefaultPipeline() {
    }

    public DefaultPipeline(Request request) {
        this.request = request;
    }

    @Override
    public Pipeline fireReceiveRequest() {
        HandlerContext.invokeReceivedRequest(head, request);
        return this;
    }

    public Pipeline addLast(Handler handler) {
        HandlerContext handlerContext = newContext(handler);
        tail.prev.next = handlerContext;
        handlerContext.prev = tail.prev;
        handlerContext.next = tail;
        tail.prev = handlerContext;
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        head = newContext(DEFAULT_HANDLER);
        tail = newContext(DEFAULT_HANDLER);
        head.next = tail;
        tail.prev = head;

        futureCollector=new FutureCollector(new ConcurrentHashMap<>());

        DecadeInventoryHandler bean = context.getBean(DecadeInventoryHandler.class);

        this.addLast(context.getBean(ValidatorHandler.class))
                .addLast(context.getBean(CommitHandler.class))
                .addLast(context.getBean(DecadeInventoryHandler.class));

    }

    private HandlerContext newContext(Handler handler) {
        HandlerContext context = this.context.getBean(HandlerContext.class);
        context.handler = handler;
        context.futureCollector=futureCollector;
        return context;
    }

    @Override
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
