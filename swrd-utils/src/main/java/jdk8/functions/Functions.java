package jdk8.functions;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class Functions<T> {

    private Object param;

    protected T target;

    public Functions() {
    }

    protected Functions(T target) {
        this.target = target;
    }

    public static <T> Functions<T> f(T target) {
        return new Functions<>(target);
    }

    public <U> Functions<U> f(Function<T, U> function) {
        return new Functions<>(function.apply(target));
    }

    public <P> Functions<T> f(P param, BiFunction<T, P, T> function) {
        return new Functions<>(function.apply(target, param));
    }

    public static <T> Functions<T> fn() {
        return new Functions();
    }

    public  <T> Functions<T> with(Object param) {
        param = param;
        return (Functions<T>) this;
    }

    public <P> Functions<T> with(P param, BiFunction<T, P, T> function) {
        return when(param, (p) -> true, function);
    }

    public <P> Functions<T> batch(Collection<P> param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    public <P> Functions<T> batch(P[] param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    public <P> Functions<T> when(P param, boolean predicate, Function<P, T> function) {
        if (predicate) {
            target = function.apply(param);
        }
        return this;
    }

    public <P> Functions<T> when(boolean predicate, Function<Object, T> function) {
        if (predicate) {
            target = function.apply(param);
        }
        return this;
    }

    public <P> Functions<T> when(P param, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target, param);
        }
        return this;
    }

    public <P> Functions<T> when(Function<T,P> func, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target,func.apply(target));
        }
        return this;
    }

    public <P> Functions<T> when(P param, Predicate<P> predicate, BiFunction<T, P, T> function) {
        return when(param, predicate.test(param), function);
    }

    public Functions<T> when(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, param, function);
    }

    public Functions<T> whenNot(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, !param, function);
    }

    public <P> Functions<T> notNull(P param, BiFunction<T, P, T> function) {
        return when(param, param != null, function);
    }

    public Functions<T> notEmpty(String param, BiFunction<T, String, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }

    public <P> Functions<T> notEmpty(Collection<P> param, BiFunction<T, Collection<P>, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }

    public <P> Functions<T> notEmpty(P[] param, BiFunction<T, P[], T> function) {
        return when(param, param != null && param.length > 0, function);
    }

    public Functions<T> gt0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() > 0, function);
    }

    public Functions<T> ge0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() >= 0, function);
    }

    public Functions<T> lt0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() < 0, function);
    }

    public Functions<T> le0(Number param, BiFunction<T, Number, T> function) {
        return when(param, param != null && param.intValue() <= 0, function);
    }

    public T f() {
        return target;
    }

}
