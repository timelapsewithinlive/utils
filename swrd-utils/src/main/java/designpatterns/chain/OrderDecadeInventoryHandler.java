package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class OrderDecadeInventoryHandler extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减库存开始");
        if(true){
            //throw new RuntimeException("扣减库存异常");
        }
        Response resp = new Response(FlagEnum.SUCCESS,"扣减库存成功");
        System.out.println("扣减库存成功");
        //return null;
        return resp;
    }

}
