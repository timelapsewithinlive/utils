package designpatterns.chan2;

/**
 * 内容解析器
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 12:13 下午
 */
public interface Bussiness<T> {
    default   T doBussiness(String content,T target){
        return null;
    };
}
