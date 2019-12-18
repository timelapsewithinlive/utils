package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class OrderValidatorHandler extends AbstractHandler implements SynHandler {

        @Override
        public Response synHandle(Request request) {
            System.out.println("参数校验开始 "+Thread.currentThread().getId());
            if(true){
               //throw  new RuntimeException("参数校验异常");
            }
            Response resp = new Response(FlagEnum.SUCCESS,"参数封装后的结果");
            System.out.println("参数校验成功");
            return resp;
        }
}
