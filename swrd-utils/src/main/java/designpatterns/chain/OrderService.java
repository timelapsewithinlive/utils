package designpatterns.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private ApplicationContext context;

    public Response mockedCreateOrder(int orderType) {
        Request request = new Request();  // request一般是通过外部调用获取
        DefaultPipeline pipeline = newPipeline(request);
        Response response = new Response(HandlerCurrentlyStatus.FAIL,null);
        try {
            System.out.println("业务开始----------------------------------------");
            //组装该请求的调用链路
            pipeline.addLast(context.getBean(OrderValidatorHandler.class));

            //不同订单类型，组装不同调用链
            if(orderType==0){
                pipeline.addLast(context.getBean(OrderGiveVipHandler.class))
                        .addLast(context.getBean(OrderDecadeInventoryHandler.class))
                        .addLast(context.getBean(OrderDecadeVoucher.class))
                        .addLast(context.getBean(OrderCommitHandler.class));
            }

            pipeline.fireReceiveRequest()//执行请求
                    .fireReturnResponse();//获取响应

            response =pipeline.response();
            if(response==null){
                System.out.println("空响应");
            }else{
                if(response.getCause()!=null){
                    StackTraceElement[] stackTrace = response.getCause().getStackTrace();
                    response.getCause().printStackTrace();
                }
               System.out.println(response);
            }
            return response;
        } finally {
            //未获取到想要的业务结果。进行业务链回滚
            if(response.getData()==null){
                pipeline.fireReleaseSource();//释放资源暂时没实现

            }
            System.out.println("业务结束----------------------------------------");
        }
    }

    private DefaultPipeline newPipeline(Request request) {
        return context.getBean(DefaultPipeline.class, request);
    }
}
