package jdk8.functions;

import com.sm.audit.commons.functions.F;
import org.jooq.Condition;
import org.jooq.Field;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * 继承自{@link F}类, 用于绑定JOOQ的OR条件
 *
 */
public class Or extends F<Condition> {
    /**
     * 私有方法
     *
     * @param target
     */
    protected Or(Condition target) {
        super(target);
    }

    /**
     * 构建对象
     *
     * @param target
     * @return
     */
    public static Or $(Condition target) {
        return new Or(target);
    }


    /**
     * 绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> com.sm.audit.commons.functions.Or with(P param, Field field,
                                                      BiFunction<Field, P, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.with(param, (c, p) -> c.or(function.apply(field, p)));
    }


    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Or batch(Collection<P> param, Field field, BiFunction<Field, P, Condition> function) {
        for (P p : param) {
            this.with(p, field, function);
        }
        return this;
    }

    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Or batch(P[] param, Field field, BiFunction<Field, P, Condition> function) {
        for (P p : param) {
            this.with(p, field, function);
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param field     表字段
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P> com.sm.audit.commons.functions.Or when(P param, Field field,
                                                      boolean predicate,
                                                      BiFunction<Field, P, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.when(param, predicate, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param field     表字段
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P> com.sm.audit.commons.functions.Or when(P param, Field field,
                                                      Predicate<P> predicate,
                                                      BiFunction<Field, P, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.when(param, predicate, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当参数param为true,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or when(boolean param, Field field,
                                                  BiFunction<Field, Boolean, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.when(param, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当参数param为false,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or whenNot(boolean param, Field field, BiFunction<Field, Boolean, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.whenNot(param, (c, p) -> c.or(function.apply(field, p)));
    }


    /**
     * 当参数不为null,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> com.sm.audit.commons.functions.Or notNull(P param, Field field, BiFunction<Field, P, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.notNull(param, (c, p) -> c.or(function.apply(field, p)));
    }


    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or notEmpty(String param, Field field, BiFunction<Field, String, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.notEmpty(param, (c, p) -> c.or(function.apply(field, p)));
    }


    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> com.sm.audit.commons.functions.Or notEmpty(Collection<P> param, Field field, BiFunction<Field, Collection<P>, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.notEmpty(param, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> com.sm.audit.commons.functions.Or notEmpty(P[] param, Field field, BiFunction<Field, P[], Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.notEmpty(param, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当参数number>0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or gt0(Number param, Field field, BiFunction<Field, Number, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.gt0(param, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当参数number>=0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or ge0(Number param, Field field, BiFunction<Field, Number, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.ge0(param, (c, p) -> c.or(function.apply(field, p)));
    }

    /**
     * 当参数number<0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or lt0(Number param, Field field, BiFunction<Field, Number, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.lt0(param, (c, p) -> c.or(function.apply(field, p)));
    }


    /**
     * 当参数number<=0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public com.sm.audit.commons.functions.Or le0(Number param, Field field, BiFunction<Field, Number, Condition> function) {
        return (com.sm.audit.commons.functions.Or) this.le0(param, (c, p) -> c.or(function.apply(field, p)));
    }

}
