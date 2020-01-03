package designpatterns.chain.test;

import designpatterns.chain.*;
import org.springframework.stereotype.Component;

@Component
public class OrderSendMqHandler extends AbstractHandler implements DownGradeHandler {
//public class OrderSendMqHandler extends AbstractHandler implements AsynHandler {


    @Override
    //@UnNecessary
    public Response asynHandle(Request request) {
        System.out.println("下单成功发送MQ开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
             /* try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           //throw new RuntimeException("发送MQ异常");
        }
        Response resp = new Response(HandlerCurrentlyStatus.SUCCESS,"发送MQ成功");
        System.out.println("下单成功发送MQ结束--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
    }

    @Override
    public void setDenpencies(Class[] denpencies) {

    }

}
