package jdk8.functions;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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

    public <U> Func<U> f(Function<T, U> function) {
        return new Func<>(function.apply(target));
    }

    public <P> Func<T> f(P param, BiFunction<T, P, T> function) {
        return new Func<>(function.apply(target, param));
    }

    public static <T> Func<T> fn() {
        return new Func();
    }

    public  <T> Func<T> with(Object param) {
        param = param;
        return (Func<T>) this;
    }

    public <P> Func<T> withBiF(P param, BiFunction<T, P, T> function) {
        return whenBiF(param, (p) -> true, function);
    }

    public <P> Func<T> batchBiF(Collection<P> param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.withBiF(p, function);
        }
        return this;
    }

    public <P> Func<T> batchBiF(P[] param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.withBiF(p, function);
        }
        return this;
    }

    public <P> Func<T> whenF(P param, boolean predicate, Function<P, T> function) {
        if (predicate) {
            target = function.apply(param);
        }
        return this;
    }

    public <P> Func<T> whenF( boolean predicate, Function<Object, T> function) {
        if (predicate) {
            target = function.apply(param);
        }
        return this;
    }

    public <P> Func<T> whenBiF(P param, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target, param);
        }
        return this;
    }

    public <P> Func<T> whenBiF(Function<T,P> func, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target,func.apply(target));
        }
        return this;
    }

    public <P> Func<T> whenC(P param, boolean predicate, Consumer<P> consumer) {
        if (predicate) {
            consumer.accept(param);
        }
        return this;
    }

    public <P> Func<T> whenBiC(Function<T,P> func, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
             function.accept(target,func.apply(target));
        }
        return this;
    }

    public <P> Func<T> whenBiC(P param, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
            function.accept(target, param);
        }
        return this;
    }

    public <P> Func<T> whenBiF(P param, Predicate<P> predicate, BiFunction<T, P, T> function) {
        return whenBiF(param, predicate.test(param), function);
    }

    public <P> Func<T> whenBiC(P param, Predicate<P> predicate, BiConsumer<T, P> function) {
        return whenBiC(param, predicate.test(param), function);
    }

    public Func<T> whenBiF(boolean param, BiFunction<T, Boolean, T> function) {
        return whenBiF(param, param, function);
    }

    public Func<T> whenBiFNot(boolean param, BiFunction<T, Boolean, T> function) {
        return whenBiF(param, !param, function);
    }

    public <P> Func<T> notNullBiF(P param, BiFunction<T, P, T> function) {
        return whenBiF(param, param != null, function);
    }

    public Func<T> notEmptyBiF(String param, BiFunction<T, String, T> function) {
        return whenBiF(param, param != null && !param.isEmpty(), function);
    }

    public <P> Func<T> notEmptyBiF(Collection<P> param, BiFunction<T, Collection<P>, T> function) {
        return whenBiF(param, param != null && !param.isEmpty(), function);
    }

    public <P> Func<T> notEmptyBiF(P[] param, BiFunction<T, P[], T> function) {
        return whenBiF(param, param != null && param.length > 0, function);
    }

    public Func<T> gt0BiF(Number param, BiFunction<T, Number, T> function) {
        return whenBiF(param, param != null && param.intValue() > 0, function);
    }

    public Func<T> ge0BiF(Number param, BiFunction<T, Number, T> function) {
        return whenBiF(param, param != null && param.intValue() >= 0, function);
    }

    public Func<T> lt0BiF(Number param, BiFunction<T, Number, T> function) {
        return whenBiF(param, param != null && param.intValue() < 0, function);
    }

    public Func<T> le0BiF(Number param, BiFunction<T, Number, T> function) {
        return whenBiF(param, param != null && param.intValue() <= 0, function);
    }

    public T f() {
        return target;
    }

}
