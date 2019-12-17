package designpatterns;

import designpatterns.tools.AsynHandler;
import designpatterns.tools.Request;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class DecadeInventoryHandler implements AsynHandler {

    @Override
    public void asynHandle(Request request) {
        System.out.println("扣减库存");
        throw new RuntimeException("扣减库存异常");
    }

}
