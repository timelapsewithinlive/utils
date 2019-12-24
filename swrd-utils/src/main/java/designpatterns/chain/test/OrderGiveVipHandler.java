package designpatterns.chain.test;

import designpatterns.chain.*;
import org.springframework.stereotype.Component;

@Component
public class OrderGiveVipHandler extends AbstractHandler implements DownGradeHandler {

    @Override
    @UnNecessary
    public Response asynHandle(Request request) {
        System.out.println("办理会员开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
             /* try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           throw new RuntimeException("办会员异常");
        }
        Response resp = new Response(HandlerCurrentlyStatus.SUCCESS,"办理会员成功");
        System.out.println("办理会员成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
    }

    @Override
    public void setDenpencies(Class[] denpencies) {

    }

}
