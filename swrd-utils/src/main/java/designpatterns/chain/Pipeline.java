package designpatterns.chain;

/**
 * 注意事项：
 *  1.模仿netty实现
 *  2.pipeline、handlerContext、response为多例。request、handler为单例
 *  3.futureCollector收集所有的异步执行结果，ContextCollector收集链路所有的上下文
 *  4.执行请求时，从头节点开始，同步handler的respones为null或者respones的状态为fail。
 *    则直接将当前handlerContext指向尾部。剔除链路上后续的handler
 *  5.执行请求出现异常时，不进行后续handler剔除（因为有异步asynHandler可能没执行完）
 *  6.获取响应时，从尾节点开始，如果response为空，判断是不是asynHandler,是就从future获取。（因为有异步asynHandler可能没执行完）
 *  7.获取响应时，异步handler的future获取结果超时、或者future获取response为空抛出异常
 *  8.当获取到最后一个执行的handler时。直接将当前handler指向尾部节点。
 *  9. handler的统一异常处理。构造response。同时赋值给当前handlerContext
 *  10. 请求synHandler可以在fail时剔除后续节点。asynHandler因为可能还没执行完成，不能剔除。在响应时。从尾部节点挨个寻找
 *
 */
public interface Pipeline {

    Pipeline fireReceiveRequest();

    Response fireReturnResponse();

    Pipeline addLast(Handler handler);
}
