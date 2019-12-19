package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class OrderDecadeInventoryHandler extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减库存开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //throw new RuntimeException("扣减库存异常");
            //Response resp = new Response(FlagEnum.FAIL,"扣减库存失败");
            //return resp;
        }
        Response resp = new Response(FlagEnum.SUCCESS,"扣减库存成功");
        System.out.println("扣减库存成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return null;
        //return resp;
    }

}
