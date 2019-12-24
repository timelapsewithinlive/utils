package designpatterns.chain.test;

import designpatterns.chain.*;
import org.springframework.stereotype.Component;

@Component
public class OrderGiveVipHandler extends AbstractHandler implements AsynHandler {

    @Override
    //@UnNecessary
    public Response asynHandle(Request request) {
        System.out.println("办理会员开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
           try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        Response resp = new Response(HandlerCurrentlyStatus.SUCCESS,"办理会员成功");
        System.out.println("办理会员成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
    }

    @Override
    public void setDenpencies(Class[] denpencies) {

    }

}
