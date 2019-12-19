package designpatterns.chain;

import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

public interface ChainFuture extends RunnableFuture {

    public void addListener(Listener listener);

}
