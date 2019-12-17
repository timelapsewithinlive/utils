package designpatterns;

import designpatterns.tools.DefaultPipeline;
import designpatterns.tools.Pipeline;
import designpatterns.tools.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ApplicationService {
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

        } finally {
        }
    }

    private Pipeline newPipeline(Request request) {
        return context.getBean(DefaultPipeline.class, request);
    }
}
