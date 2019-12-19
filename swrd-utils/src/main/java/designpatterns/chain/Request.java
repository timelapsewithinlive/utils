package designpatterns.chain;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 请求参数封装体
 */
public class Request {

    public AtomicBoolean isPropagation =new AtomicBoolean(true);//handlerContext是否继续向下传播标识

    private ContextCollector contextCollector;

    public ContextCollector getContextCollector() {
        return contextCollector;
    }

    public void setContextCollector(ContextCollector contextCollector) {
        this.contextCollector = contextCollector;
    }


}
