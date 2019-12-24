package designpatterns.chain.test;

import designpatterns.chain.*;
import org.springframework.stereotype.Component;

@Component
public class OrderDecadeInventoryHandler extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减库存开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //throw new RuntimeException("扣减库存异常");
            //Response resp = new Response(FlagEnum.FAIL,"扣减库存失败");
            //return resp;
            //return null;
        }
        Response resp = new Response(HandlerCurrentlyStatus.SUCCESS,"扣减库存成功");
        //Response resp = new Response(FlagEnum.FAIL,"扣减库存失败");
        System.out.println("扣减库存成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
        //return resp;
    }

    @Override
    public void setDenpencies(Class[] denpencies) {

    }
}
