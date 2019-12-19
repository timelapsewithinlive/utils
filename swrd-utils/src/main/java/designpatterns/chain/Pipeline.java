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
 *  15.新改造的。如果线程池满，当前线程执行任务，如果返回的respones为null或者状态标识为fail。也不再进行传播
 *  16.扩展jdk的Future,可以添加监听器。在任务执行完时。直接将结果赋值给handlerContext
 *  17.扩展jdk的线程池。可以添加监听器和提交扩展的future
 *  18. request维护一个是否传播的全局标识。异步handler在监听器里通过乐观锁设置标识是否继续传播
 *  19. 执行事物方法前必须判断前边的异步handler是否都执行完毕
 *  20. 可能出现后一个异步handler执行成功，但前一个异步handler执行失败但没有将全局标识修改的情况
 *  21. 全局的传播标识只是为了尽早的发现异常节点。不再进行传播调用。不具备链传递的整体原子性
 *  22. 因为链中存在异步，无法考虑整体的原子性。只能通过业务手段判断
 *  23. 后续可以利用反射，在每一个handler执行前，验证是否增加了@ChainTransactionnal注解。如果是。则认为是
 *      事物方法，必须等待前边的异步节点全部执行完成。 通过代理模式实现比较好
 *  24. 不要在一个链中产生多个事物handler。更不要在异步handler里产生事物。不然业务侧很难控制
 *  25. 如果业务侧handler中要做降级。那就再业务失败时。将response中的状态标识为SUCCESS
 *
 */
public interface Pipeline {

    Pipeline fireReceiveRequest();

    Pipeline fireReturnResponse();

    Pipeline fireReleaseSource();

    Pipeline addLast(Handler handler);
}
