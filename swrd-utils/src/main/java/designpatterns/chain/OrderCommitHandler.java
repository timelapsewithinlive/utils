package designpatterns.chain;

import org.springframework.stereotype.Component;

/**
 * 提交订单属于事物方法，应该判断前边所有的异步handler是否执行成功，再去执行
 * 否则，不执行事物
 */
@Component
public class OrderCommitHandler extends AbstractHandler implements SynHandler {

    @Override
    @ChainTransactional
    public Response synHandle(Request request) {
        System.out.println("订单提交开始--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        if(true){
           /* try {

            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //return new Response(FlagEnum.FAIL,"订单提交失败");

           //throw  new RuntimeException("提交订单系统异常");

        }
        Response resp = new Response(FlagEnum.SUCCESS,"订单号："+60149759441046l);
        System.out.println("订单提交成功--线程ID："+Thread.currentThread().getId()+"--当前时间: "+System.currentTimeMillis());
        return resp;
        //return null;
    }

    @Override
    public void setDenpencies(Class[] denpencies) {

    }
}
