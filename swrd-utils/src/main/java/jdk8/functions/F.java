package jdk8.functions;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 */
public class F<T> {

    /**
     * 绑定对象
     */
    protected T target;

    /**
     * 私有方法
     *
     * @param target
     */
    protected F(T target) {
        this.target = target;
    }

    /**
     * 构建对象
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T> F<T> $(T target) {
        return new F<>(target);
    }

    /**
     * 执行函数,重新绑定返回结果
     *
     * @param function
     * @param <U>      函数返回类型
     * @return
     */
    public <U> F<U> $(Function<T, U> function) {
        return new F<>(function.apply(target));
    }

    /**
     * 执行函数,重新绑定返回结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      函数第二个参数类型
     * @return
     */
    public <P> F<T> $(P param, BiFunction<T, P, T> function) {
        return new F<>(function.apply(target, param));
    }

    /**
     * 绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> with(P param, BiFunction<T, P, T> function) {
        return when(param, (p) -> true, function);
    }


    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> batch(Collection<P> param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> batch(P[] param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> when(P param, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target, param);
        }
        return this;
    }

    public <P> F<T> whenFunc(Function<T,P> func, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target,func.apply(target));
        }
        return this;
    }

    public <P> F<T> whenFuncDefault(Function<T,P> func, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
             function.accept(target,func.apply(target));
        }
        return this;
    }

    public <P> F<T> when(P param, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
            function.accept(target, param);
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> when(P param, Predicate<P> predicate, BiFunction<T, P, T> function) {
        return when(param, predicate.test(param), function);
    }

    public <P> F<T> when(P param, Predicate<P> predicate, BiConsumer<T, P> function) {
        return when(param, predicate.test(param), function);
    }

    /**
     * 当参数param为true,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> when(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, param, function);
    }

    /**
     * 当参数param为false,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> whenNot(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, !param, function);
    }

    /**
     * 当参数不为null,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> notNull(P param, BiFunction<T, P, T> function) {
        return when(param, param != null, function);
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> notEmpty(String param, BiFunction<T, String, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }


    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> notEmpty(Collection<P> param, BiFunction<T, Collection<P>, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> F<T> notEmpty(P[] param, BiFunction<T, P[], T> function) {
        return when(param, param != null && param.length > 0, function);
    }

    /**
     * 当参数param>0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> gt0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() > 0, function);
    }

    /**
     * 当参数param>=0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> ge0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() >= 0, function);
    }

    /**
     * 当参数param<0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> lt0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() < 0, function);
    }

    /**
     * 当参数param<=0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public F<T> le0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() <= 0, function);
    }


    /**
     * 获取绑定对象
     *
     * @return
     */
    public T $() {
        return target;
    }

}
