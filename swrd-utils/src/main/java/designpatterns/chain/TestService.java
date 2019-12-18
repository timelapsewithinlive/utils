package designpatterns.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestService {
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() throws InterruptedException {
        //Thread.sleep(5000);
        mockedClient();
    }

    public void mockedClient() {
        Request request = new Request();  // request一般是通过外部调用获取
        DefaultPipeline pipeline = newPipeline(request);
        try {
            pipeline.fireReceiveRequest();//执行请求
            pipeline.fireReturnResponse();//获取响应
            Response response =pipeline.tail.response;//找到最后一个执行的handler,将结果放入尾部节点上
            System.out.println(response);
            if(response.getCause()!=null){
                StackTraceElement[] stackTrace = response.getCause().getStackTrace();
                response.getCause().printStackTrace();
            }
        } finally {
            pipeline.fireReleaseSource();//释放资源暂时没实现
        }
    }

    private DefaultPipeline newPipeline(Request request) {
        return context.getBean(DefaultPipeline.class, request);
    }
}
