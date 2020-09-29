package designpatterns.chan2;

import lombok.Data;

/**
 * 请求参数封装体
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:14 上午
 */
@Data
public class Request<T> {

    /**
     * 参数内容
     */
    private String content;

    /**
     * 目标载体
     */
    private T t;

    public Request(String content, T t) {
        this.content = content;
        this.t = t;
    }
}
