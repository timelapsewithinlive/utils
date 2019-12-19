package designpatterns.chain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注责任链某节点是否为事物节点。
 * 事物节点必须等待前边的异步节点都执行成功
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ChainTransactional {

}
