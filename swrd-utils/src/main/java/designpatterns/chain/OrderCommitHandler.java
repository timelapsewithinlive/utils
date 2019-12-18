package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class OrderCommitHandler extends AbstractHandler implements SynHandler {

    @Override
    public Response synHandle(Request request) {
        System.out.println("订单提交开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        Response resp = new Response(FlagEnum.SUCCESS,"订单号："+60149759441046l);
        if(true){
            //return new Response(FlagEnum.FAIL,"订单提交失败");

           //throw  new RuntimeException("提交订单系统异常");
        }
        System.out.println("订单提交成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
        // return null;
    }
}
