package designpatterns.command.trade;

import designpatterns.chain.test.OrderSubmitService;

/**
 * @Author honglin.xhl
 * @Date 2020/9/21 7:11 下午
 */
public enum TradeCommandEnum {
    CREATE_ORDER("createOrder", OrderSubmitService.class);

    private String service;

    private Class serviceClass;

    TradeCommandEnum(String service, Class serviceClass) {
        this.service = service;
        this.serviceClass = serviceClass;
    }

    public static Class of(String service) {
        TradeCommandEnum[] values = TradeCommandEnum.values();
        for (TradeCommandEnum transfer : values) {
            if(transfer.service.equals(service)){
                return transfer.serviceClass;
            }
        }
        return null;
    }
}
