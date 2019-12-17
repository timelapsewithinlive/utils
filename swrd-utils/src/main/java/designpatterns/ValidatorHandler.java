package designpatterns;

import designpatterns.tools.Request;
import designpatterns.tools.SynHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ValidatorHandler implements SynHandler {

        @Override
        public void synHandle(Request request) {
            System.out.println("参数校验");
        }
}
