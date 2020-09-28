package jdk8.functions;

/**
 */
public class Throws {

    /**
     * 异常抛出
     *
     * @param t
     * @param <T>
     * @param <R>
     * @return
     * @throws T
     */
    static <T extends Throwable, R> R sneakyThrow(Throwable t) throws T {
        throw (T)t;
    }
}
