package designpatterns.chain.test;

import designpatterns.chain.DefaultPipeline;
import designpatterns.chain.HandlerCurrentlyStatus;
import designpatterns.chain.Request;
import designpatterns.chain.Response;
import designpatterns.chain.test.*;
import designpatterns.strategy.OrderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class OrderSubmitService implements RpcService{
    @Autowired
    private ApplicationContext context;

    @Override
    public void doBussiness(Request request) {
        Response  response =null;
        DefaultPipeline pipeline =null;;
        try {
            Date date = new Date();
            SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("业务开始---------------------------------------------------------"+ time.format(date));
            pipeline = OrderStrategy.Submit.of(request);
            pipeline.fireReceiveRequest()//执行请求
                    .fireReturnResponse();//获取响应

            response =pipeline.response();
            if(response==null){
                System.out.println("空响应");
            }else{
                if(response.getCause()!=null){
                    StackTraceElement[] stackTrace = response.getCause().getStackTrace();
                    //response.getCause().printStackTrace();
                }
               System.out.println(response);
            }
        }catch (Exception e){
        }finally {
            //未获取到想要的业务结果。进行业务链回滚
            if(response.getData()==null){
                pipeline.fireReleaseSource();//释放资源暂时没实现

            }
            Date date2 = new Date();
            SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("业务结束-----------------------------------------------------------"+ time.format(date2));
        }
    }
}
