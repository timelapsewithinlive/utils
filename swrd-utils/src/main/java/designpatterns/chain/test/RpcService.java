package designpatterns.chain.test;

import designpatterns.chain.Request;

import java.util.Map;

/**
 * @Author honglin.xhl
 * @Date 2020/9/21 8:01 下午
 */
public interface RpcService {
    public void doBussiness(Request request);
}
