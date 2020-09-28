
package jdk8.functions;


@FunctionalInterface
public interface CheckedRunnable {

    static CheckedRunnable of(CheckedRunnable methodReference) {
        return methodReference;
    }

    void run() throws Throwable;

    default Runnable unchecked() {
        return () -> {
            try {
                run();
            } catch(Throwable x) {
                Throws.sneakyThrow(x);
            }
        };
    }
}


