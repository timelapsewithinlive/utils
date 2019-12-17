package designpatterns.chain;

import org.springframework.stereotype.Component;

@Component
public class CommitHandler extends AbstractHandler implements SynHandler {

    @Override
    public Response synHandle(Request request) {
        System.out.println("订单提交");
        Response resp = new Response(0,"订单提交成功",null);
        return resp;
    }
}
