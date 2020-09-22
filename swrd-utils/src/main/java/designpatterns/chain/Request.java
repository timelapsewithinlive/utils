package designpatterns.chain;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 请求参数封装体
 */
public class Request {

    public AtomicBoolean isPropagation =new AtomicBoolean(true);//handlerContext是否继续向下传播标识

    public int TransactionWaitTimeOut=Config.TRANSACTION_WAIT_TIME_OUT;

    public CountDownLatch countDownLatch;

    private ContextCollector contextCollector;

    public ContextCollector getContextCollector() {
        return contextCollector;
    }

    public void setContextCollector(ContextCollector contextCollector) {
        this.contextCollector = contextCollector;
    }

    public Map<String,Object> param;

    public Request(Map<String, Object> param) {
        this.param = param;
    }


}
