package jdk8.functions;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


public class Func<T> {

    protected T target;

    protected Func(T target) {
        this.target = target;
    }

    public static <T> Func<T> $(T target) {
        return new Func<>(target);
    }

    public <U> Func<U> $(Function<T, U> function) {
        return new Func<>(function.apply(target));
    }

    public <P> Func<T> $(P param, BiFunction<T, P, T> function) {
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

    public <P> Func<T> whenFunc(Function<T,P> func, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target,func.apply(target));
        }
        return this;
    }

    public <P> Func<T> whenFuncDefault(Function<T,P> func, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
             function.accept(target,func.apply(target));
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

    public T $() {
        return target;
    }

}
