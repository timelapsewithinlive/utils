package designpatterns.chain;

public class Config {

    public final static int THREAD_POOL_NUM =Runtime.getRuntime().availableProcessors()*2;

    public final static int THREAD_POOL_KEEP_ALIVE_TIME =60000;

    public final static int FUTURE_TIME_OUT =10;
}
