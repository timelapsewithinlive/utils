package designpatterns.chain;/*
package designpatterns;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OrderProcessHandlerEnum {
    PLAT_STORE(1, ValidatorHandler.class),
    UN_PALT_SOTRE(2, CommitHandler.class),
    SELF_SUPPORT(-1, DecadeInventoryHandler.class),
    ;

    private Integer type;

    private Class strategy;

    OrderProcessHandlerEnum(int type, Class strategy) {
        this.type = type;
        this.strategy = strategy;
    }

    static Map<Integer, OrderProcessHandlerEnum> STRATEGY_ENUM = new ConcurrentHashMap(){
        {
            OrderProcessHandlerEnum[] values = OrderProcessHandlerEnum.values();
            for(OrderProcessHandlerEnum strategy : values){
                this.put(strategy.getType(),strategy);
            }
        }
    };

    static Map<Integer, Strategy> STRATEGY_MAP = new ConcurrentHashMap();

    public static Strategy calcStrategy(int type) {
        Strategy strategy = STRATEGY_MAP.get(type);
        if (strategy == null) {
            synchronized (Strategy.class) {
                if(strategy==null){
                    OrderProcessHandlerEnum deliveryFeeStrategyEnum = STRATEGY_ENUM.get(type);
                    Class strategyClass = deliveryFeeStrategyEnum.getStrategy();
                    try {
                        strategy = (Strategy) strategyClass.newInstance();
                    } catch (Exception e) {

                    }
                    STRATEGY_MAP.put(type, strategy);
                }
            }
        }
        return strategy;
    }

    public static OrderProcessHandlerEnum getStrategyEnum(int type){
        return STRATEGY_ENUM.get(type);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Class getStrategy() {
        return strategy;
    }

    public void setStrategy(Class strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args){
        System.out.println(calcStrategy(1));
        System.out.println(calcStrategy(1));
    }
}


*/
