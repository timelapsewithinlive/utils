package designpatterns.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OrderService {
    @Autowired
    private ApplicationContext context;

    public Response mockedCreateOrder(int orderType) {
        Request request = new Request();  // request一般是通过外部调用获取
        DefaultPipeline pipeline = newPipeline(request);
        try {
            //组装该请求的调用链路
            pipeline.addLast(context.getBean(OrderValidatorHandler.class));

            //不同订单类型，组装不同调用链
            if(orderType==0){
                pipeline.addLast(context.getBean(OrderDecadeInventoryHandler.class))
                        .addLast(context.getBean(OrderDecadeVoucher.class))
                        .addLast(context.getBean(OrderCommitHandler.class));
            }

            pipeline.fireReceiveRequest()//执行请求
                    .fireReturnResponse();//获取响应
            Response response =pipeline.tail.response;//找到最后一个执行的handler,将结果放入尾部节点上
            System.out.println(response);
            if(response.getCause()!=null){
                StackTraceElement[] stackTrace = response.getCause().getStackTrace();
                response.getCause().printStackTrace();
            }
            return response;
        } finally {
            pipeline.fireReleaseSource();//释放资源暂时没实现
        }
    }

    private DefaultPipeline newPipeline(Request request) {
        return context.getBean(DefaultPipeline.class, request);
    }
}
