package designpatterns.strategy;


import designpatterns.chain.DefaultPipeline;
import designpatterns.chain.Request;
import designpatterns.chain.test.OrderCommitHandler;
import designpatterns.chain.test.OrderDecadeInventoryHandler;
import designpatterns.chain.test.OrderDecadeVoucher;
import designpatterns.chain.test.OrderGiveVipHandler;
import designpatterns.chain.test.OrderSendMqHandler;
import designpatterns.chain.test.OrderValidatorHandler;
import spring.ApplicationContextHolder;

/**
 * 业务逻辑决策器
 *
 * @author honglin.xhl
 */
public interface OrderStrategy {

    /**
     * 计算具体的业务执行类
     *
     * @return
     */
    DefaultPipeline assemble(Request request) throws NoSuchMethodException;

    default DefaultPipeline newPipeline(Request request) {
        return ApplicationContextHolder.getBean(DefaultPipeline.class, request);
    }

    /**
     * 充值
     */
    enum Submit implements OrderStrategy {

        App("app") {
            @Override
            public DefaultPipeline assemble(Request request) throws NoSuchMethodException {
                DefaultPipeline pipeline = newPipeline(request);
                pipeline.addLast(ApplicationContextHolder.getBean(OrderValidatorHandler.class))
                        .addLast(ApplicationContextHolder.getBean(OrderGiveVipHandler.class))
                        .addLast(ApplicationContextHolder.getBean(OrderDecadeInventoryHandler.class))
                        .addLast(ApplicationContextHolder.getBean(OrderDecadeVoucher.class))
                        .addLast(ApplicationContextHolder.getBean(OrderCommitHandler.class))
                        .addLast(ApplicationContextHolder.getBean(OrderSendMqHandler.class));
                return pipeline;
            }
        },

        H5("H5") {
            @Override
            public DefaultPipeline assemble(Request request) {
                return null;
            }
        },

        WeChat("WeChat") {
            @Override
            public DefaultPipeline assemble(Request request) {
                return null;
            }
        }
        ;
        private String orderSource;

        Submit(String orderSource) {
            this.orderSource = orderSource;
        }

        public static DefaultPipeline of(Request request) throws NoSuchMethodException {
            Submit[] values = Submit.values();
            for (Submit submit :values){
                if(submit.orderSource.equals(request.param.get("orderSource"))){
                    return submit.assemble(request);
                }
            }
            return null;
        }
    }

}
