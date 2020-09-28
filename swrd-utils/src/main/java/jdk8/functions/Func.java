package jdk8.functions;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


public class Func<T> {

    private Object param;

    protected T target;

    public Func() {
    }

    protected Func(T target) {
        this.target = target;
    }

    public static <T> Func<T> f(T target) {
        return new Func<>(target);
    }

    public static <T> Func<T> fn() {
        return new Func();
    }

    public  <T> Func<T> with(Object param) {
        param = param;
        return (Func<T>) this;
    }

    public <U> Func<U> f(Function<T, U> function) {
        return new Func<>(function.apply(target));
    }

    public <P> Func<T> f(P param, BiFunction<T, P, T> function) {
        return new Func<>(function.apply(target, param));
    }

    public <P> Func<T> with(P param, BiFunction<T, P, T> function) {
        return when(param, (p) -> true, function);
    }

    public <P> Func<T> batch(Collection<P> param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    public <P> Func<T> batch(P[] param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    public <P> Func<T> when(P param, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target, param);
        }
        return this;
    }

    public <P> Func<T> when(Function<T,P> func, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target,func.apply(target));
        }
        return this;
    }

    public <P> Func<T> whenDefault(Function<T,P> func, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
             function.accept(target,func.apply(target));
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param
     * @param predicate
     * @param function
     * @param <P>
     * @return
     */
    public <P> Func<T> whenApply(P param, boolean predicate, Function<P, T> function) {
        if (predicate) {
            target = function.apply(param);
        }
        return this;
    }

    public <P> Func<T> whenApply( boolean predicate, Function<Object, T> function) {
        if (predicate) {
            target = function.apply(param);
        }
        return this;
    }

    public <P> Func<T> when(P param, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
            function.accept(target, param);
        }
        return this;
    }

    public <P> Func<T> when(P param, Predicate<P> predicate, BiFunction<T, P, T> function) {
        return when(param, predicate.test(param), function);
    }

    public <P> Func<T> when(P param, Predicate<P> predicate, BiConsumer<T, P> function) {
        return when(param, predicate.test(param), function);
    }

    public Func<T> when(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, param, function);
    }

    public Func<T> whenNot(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, !param, function);
    }

    public <P> Func<T> notNull(P param, BiFunction<T, P, T> function) {
        return when(param, param != null, function);
    }

    public Func<T> notEmpty(String param, BiFunction<T, String, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }

    public <P> Func<T> notEmpty(Collection<P> param, BiFunction<T, Collection<P>, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }

    public <P> Func<T> notEmpty(P[] param, BiFunction<T, P[], T> function) {
        return when(param, param != null && param.length > 0, function);
    }

    public Func<T> gt0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() > 0, function);
    }

    public Func<T> ge0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() >= 0, function);
    }

    public Func<T> lt0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() < 0, function);
    }

    public Func<T> le0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() <= 0, function);
    }

    public T f() {
        return target;
    }

}
