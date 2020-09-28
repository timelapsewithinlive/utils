package jdk8.functions;

import java.util.Objects;
import java.util.function.Function;

/**
 * 三元函数
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <R>
 *
 */
@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }

}