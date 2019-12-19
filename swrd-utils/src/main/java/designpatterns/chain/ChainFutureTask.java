package designpatterns.chain;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ChainFutureTask extends FutureTask implements ChainFuture{
    private Listener listener;

    public ChainFutureTask(Callable callable) {
        super(callable);
    }

    public ChainFutureTask(Runnable runnable, Object result) {
        super(runnable, result);
    }

    @Override
    public void addListener(Listener listener) {
        setListener(listener);
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void set(Object o) {
        listener.listen((Response)o);
        super.set(o);
    }
}
