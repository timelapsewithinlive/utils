package jdk8.functions;

import java.util.Objects;
import java.util.function.Function;

import static jdk8.functions.Throws.sneakyThrow;

@FunctionalInterface
public interface CheckedFunction<T, R> {

    static <T, R> CheckedFunction<T, R> of(CheckedFunction<T, R> methodReference) {
        return methodReference;
    }

    @SuppressWarnings("unchecked")
    static <T, R> CheckedFunction<T, R> narrow(CheckedFunction<? super T, ? extends R> f) {
        return (CheckedFunction<T, R>) f;
    }

    static <T> CheckedFunction<T, T> identity() {
        return t -> t;
    }


    R apply(T t) throws Throwable;

    default Function<T, R> recover(Function<? super Throwable, ? extends Function<? super T, ? extends R>> recover) {
        Objects.requireNonNull(recover, "recover is null");
        return (t) -> {
            try {
                return this.apply(t);
            } catch (Throwable throwable) {
                final Function<? super T, ? extends R> func = recover.apply(throwable);
                Objects.requireNonNull(func,
                        () -> "recover return null for " + throwable.getClass() + ": " + throwable.getMessage());
                return func.apply(t);
            }
        };
    }

    default Function<T, R> unchecked() {
        return (t) -> {
            try {
                return apply(t);
            } catch (Throwable x) {
                return sneakyThrow(x);
            }
        };
    }

    default <V> CheckedFunction<T, V> andThen(CheckedFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after, "after is null");
        return (t) -> after.apply(apply(t));
    }


    default <V> CheckedFunction<V, R> compose(CheckedFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before, "before is null");
        return v -> apply(before.apply(v));
    }

}