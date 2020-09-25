package designpatterns.command;

import designpatterns.chain.Request;
import designpatterns.chain.test.RpcService;
import designpatterns.command.trade.TradeCommandEnum;
import spring.ApplicationContextHolder;

import java.util.Map;

/**
 * @Author honglin.xhl
 * @Date 2020/9/16 11:24 上午
 */
public interface Command {

   default void execute(Request request){
       //采用RPC的泛化调用拼接服务,省略.......
       //这里模拟已经拼接好泛化服务
      Class service = TradeCommandEnum.of(request.param.get("service") + "");
      RpcService rpcService =  (RpcService)ApplicationContextHolder.getBean(service);
      rpcService.doBussiness(request);
   }

}
