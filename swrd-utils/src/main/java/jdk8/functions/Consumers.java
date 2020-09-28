package jdk8.functions;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author honglin.xhl
 * @Date 2020/9/28 8:52 下午
 */
public class Consumers<T> {
    private Object param;

    protected T target;

    public Consumers() {
    }

    public Consumers(T target) {
        this.target = target;
    }

    public static <T> Consumers<T> $(T target) {
        return new Consumers<>(target);
    }

    public <P> Consumers<T> when(P param, boolean predicate, Consumer<P> consumer) {
        if (predicate) {
            consumer.accept(param);
        }
        return this;
    }

    public <P> Consumers<T> when(Function<T,P> func, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
            function.accept(target,func.apply(target));
        }
        return this;
    }

    public <P> Consumers<T> when(P param, boolean predicate, BiConsumer<T, P> function) {
        if (predicate) {
            function.accept(target, param);
        }
        return this;
    }

    public <P> Consumers<T> when(P param, Predicate<P> predicate, BiConsumer<T, P> function) {
        return when(param, predicate.test(param), function);
    }

    public T $() {
        return target;
    }

}
