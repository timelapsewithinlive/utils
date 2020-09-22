package designpatterns.facede;

import designpatterns.chain.Request;
import designpatterns.command.Command;
import spring.ApplicationContextHolder;

import java.util.Map;

/**
 * 门面模式用来定位部门的业务，如：购物车、交易、支付、物流、客户服务等
 *
 * @Author honglin.xhl
 * @Date 2020/9/16 11:27 上午
 */
public interface Facede {

    default void guide(Request request) {

    }

}
