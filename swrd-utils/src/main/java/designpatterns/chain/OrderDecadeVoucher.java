package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class OrderDecadeVoucher extends AbstractHandler implements AsynHandler {

    @Override
    public Response asynHandle(Request request) {
        System.out.println("扣减优惠券开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //throw new RuntimeException("扣减优惠券异常");
        }
        Response resp = new Response(FlagEnum.SUCCESS,"扣减优惠券成功");
        System.out.println("扣减优惠券成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
        //return resp;
    }
}
