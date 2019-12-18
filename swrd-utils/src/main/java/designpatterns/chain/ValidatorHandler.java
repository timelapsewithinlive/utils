package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class ValidatorHandler extends AbstractHandler implements SynHandler {

        @Override
        public Response synHandle(Request request) {
            System.out.println("参数校验");
            System.out.println("ValidatorHandler thread name: "+Thread.currentThread().getName());
            Response resp = new Response(FlagEnum.SUCCESS,"参数封装后的结果");
            return resp;
        }
}
