package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class ValidatorHandler extends AbstractHandler implements SynHandler {

        @Override
        public Response synHandle(Request request) {
            System.out.println("参数校验开始");
            Response resp = new Response(FlagEnum.SUCCESS,"参数封装后的结果");
            System.out.println("参数校验成功");
            return resp;
        }
}
