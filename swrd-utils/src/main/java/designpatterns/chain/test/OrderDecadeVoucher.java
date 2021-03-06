package designpatterns.chain.test;

import designpatterns.chain.*;
import org.springframework.stereotype.Component;

@Component
public class OrderDecadeVoucher extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减优惠券开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
            /*try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           //throw new RuntimeException("扣减优惠券异常");
        }
        //Response resp = new Response(HandlerCurrentlyStatus.FAIL,"扣减优惠券失败");
        Response resp = new Response(HandlerCurrentlyStatus.SUCCESS,"扣减优惠券成功");
        System.out.println("扣减优惠券成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
        //return resp;
    }

    @Override
    public void setDenpencies(Class[] denpencies) {

    }
}
