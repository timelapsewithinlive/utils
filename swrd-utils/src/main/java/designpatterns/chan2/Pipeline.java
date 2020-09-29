package designpatterns.chan2;

/**
 * 责任链管理器
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:14 上午
 */
public interface Pipeline {

    /**
     * 执行请求
     *
     * @return
     */
    Pipeline fireReceiveRequest();

    /**
     * 获取结果
     *
     * @return
     */
    Pipeline fireReturnResponse();

    /**
     * 添加链尾部添加处理器
     *
     * @param handler
     * @return
     */
    Pipeline addLast(Handler handler);
}
