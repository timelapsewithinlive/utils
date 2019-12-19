package designpatterns.chain;

/**
 * 请求参数封装体
 */
public class Request {
    public volatile boolean isPropagation=true;//handlerContext是否继续向下传播标识

    private ContextCollector contextCollector;

    public ContextCollector getContextCollector() {
        return contextCollector;
    }

    public void setContextCollector(ContextCollector contextCollector) {
        this.contextCollector = contextCollector;
    }


}
