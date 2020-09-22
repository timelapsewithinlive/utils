package designpatterns.command.trade;

import designpatterns.chain.Request;
import designpatterns.command.Command;
import org.springframework.stereotype.Service;
import spring.ApplicationContextHolder;

/**
 * @Author honglin.xhl
 * @Date 2020/9/22 8:04 下午
 */
@Service
public class TradeService {

    public void receive(Request request){
        String interfaceName = request.param.get("service") + "";
        Command command = (Command) ApplicationContextHolder.getBean(interfaceName);
        command.execute(request);
    }
}
