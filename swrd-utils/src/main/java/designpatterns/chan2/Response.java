package designpatterns.chan2;

import lombok.Data;

/**
 * 响应封装体
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:14 上午
 */
@Data
public class Response {

    /**
     * 执行结果状态
     */
    private CurrentlyStatus flag;

    /**
     * 异常原因
     */
    private Throwable cause;

    /**
     * 响应数据
     */
    private Object data;

    public Response() {
    }

    public Response(CurrentlyStatus flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "flag:" + flag +
                ", cause:" + cause +
                ", data:" + data +
                ", 当前时间:" + System.currentTimeMillis() +
                '}';
    }
}
