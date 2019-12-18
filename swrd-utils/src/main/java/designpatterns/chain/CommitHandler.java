package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class CommitHandler extends AbstractHandler implements SynHandler {

    @Override
    public Response synHandle(Request request) {
        System.out.println("订单提交");
        Response resp = new Response(FlagEnum.SUCCESS,653422897);
        if(true){
            //resp = new Response(FlagEnum.FAIL,"订单提交成功");

            // throw  new RuntimeException("提交订单系统异常");
        }
        return resp;
    }
}
