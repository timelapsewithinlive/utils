package designpatterns;

import designpatterns.tools.AbstractHandler;
import designpatterns.tools.Request;
import designpatterns.tools.SynHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ValidatorHandler extends AbstractHandler implements SynHandler {

        @Override
        public void synHandle(Request request) {
            System.out.println("参数校验");
        }
}
