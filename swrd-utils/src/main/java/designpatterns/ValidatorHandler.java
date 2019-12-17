package designpatterns;

import designpatterns.tools.AbstractHandler;
import designpatterns.tools.Request;
import designpatterns.tools.Response;
import designpatterns.tools.SynHandler;
import org.springframework.stereotype.Component;

@Component
public class ValidatorHandler extends AbstractHandler implements SynHandler {

        @Override
        public Response synHandle(Request request) {
            System.out.println("参数校验");
            System.out.println("ValidatorHandler thread name: "+Thread.currentThread().getName());
            return null;
        }
}
