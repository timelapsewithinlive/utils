package designpatterns;

import designpatterns.tools.AbstractHandler;
import designpatterns.tools.AsynHandler;
import designpatterns.tools.Request;
import designpatterns.tools.Response;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
public class DecadeInventoryHandler extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减库存");
        if(false){
            throw new RuntimeException("扣减库存异常");
        }
        return new Response();
    }

}
