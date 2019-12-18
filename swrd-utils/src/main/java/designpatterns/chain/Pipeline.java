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
 *  10.请求synHandler可以在fail时剔除后续节点。asynHandler因为可能还没执行完成，不能剔除。在响应时。从尾部节点挨个寻找
 *  11.asynHandler如果在链表中间调用，就是异步后边还有同步，例如下单。如果出现业务异常，需要抛出异常。除非实现release方法。进行遍历判断每一个
 *     response。是不是有fail状态节点。如果有进行事物回滚，其它资源释放
 *  12.建议asynHandler出现业务异常，直接抛出异常。否则后续的节点还会继续执行
 *  13.如果不想处理11和12步。事物方法应该判断所有的asynHandler是不是执行成功。否则进行事物回滚
 *  14.抛出业务异常时，建议使用ExceptionWithoutTraceStack。不会收集栈信息，节省性能开销
 */
public interface Pipeline {

    Pipeline fireReceiveRequest();

    Response fireReturnResponse();

    Pipeline addLast(Handler handler);
}
