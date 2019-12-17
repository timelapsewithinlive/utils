package designpatterns;

import designpatterns.tools.AbstractHandler;
import designpatterns.tools.Request;
import designpatterns.tools.SynHandler;
import org.springframework.stereotype.Component;

@Component
public class CommitHandler extends AbstractHandler implements SynHandler {

    @Override
    public void synHandle(Request request) {
        System.out.println("订单提交");
    }
}
