package designpatterns;

import designpatterns.tools.*;
import org.springframework.stereotype.Component;

@Component
public class DecadeInventoryHandler extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减库存");
        if(false){
            throw new RuntimeException("扣减库存异常");
        }
        System.out.println("DecadeInventoryHandler thread name: "+Thread.currentThread().getName());
        Response resp = new Response(0,"扣减库存成功",null);
        return resp;
    }



}
