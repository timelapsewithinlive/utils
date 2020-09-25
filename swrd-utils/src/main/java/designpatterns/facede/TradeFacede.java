package designpatterns.facede;

import designpatterns.chain.Request;
import designpatterns.command.Command;
import designpatterns.command.trade.TradeService;
import org.springframework.stereotype.Component;
import spring.ApplicationContextHolder;

import javax.annotation.Resource;

/**
 * @Author honglin.xhl
 * @Date 2020/9/16 11:28 上午
 */
@Component
public class TradeFacede implements Facede{

    @Resource
    TradeService tradeService;

    @Override
    public void guide(Request request) {
        tradeService.receive(request);
    }

}
