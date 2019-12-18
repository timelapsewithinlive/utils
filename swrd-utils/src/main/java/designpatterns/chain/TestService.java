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
        Pipeline pipeline = newPipeline(request);
        try {
            pipeline.fireReceiveRequest();
            Response response = pipeline.fireReturnResponse();
            System.out.println(response);
            if(response.getCause()!=null){
                StackTraceElement[] stackTrace = response.getCause().getStackTrace();
                response.getCause().printStackTrace();
            }
        } finally {

        }
    }

    private Pipeline newPipeline(Request request) {
        return context.getBean(DefaultPipeline.class, request);
    }
}
