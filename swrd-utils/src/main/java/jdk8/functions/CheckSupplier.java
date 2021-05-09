package jdk8.functions;

import java.util.function.Supplier;

import static jdk8.functions.Throws.sneakyThrow;


/**
 * @author xinghonglin
 * @date 2021/03/29
 */
@FunctionalInterface
public interface CheckSupplier<R> {

    static <R> CheckSupplier<R> of(CheckSupplier<R> methodReference) {
        return methodReference;
    }

    R apply() throws Throwable;

    default Supplier<R> unchecked() {
        return () -> {
            try {
                return apply();
            } catch (Throwable x) {
                return sneakyThrow(x);
            }
        };
    }

}
