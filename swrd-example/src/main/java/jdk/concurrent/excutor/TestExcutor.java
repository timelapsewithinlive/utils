package jdk.concurrent.excutor;

import java.util.concurrent.*;

public class TestExcutor {

    static ExecutorService executor = Executors.newFixedThreadPool(3);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,60000, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1),new ThreadPoolExecutor.DiscardPolicy());
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("线程执行");
                return "返回值";
            }
        };
        test(callable);
        //Future<String> submit = executor.submit(callable);

        //threadPoolExecutor.shutdown();
    }

    public static void test (Callable callable) throws ExecutionException, InterruptedException {
        Future<String> submit =threadPoolExecutor.submit(callable);
        System.out.println(submit.get());
        System.out.println("activethread:"+threadPoolExecutor.getActiveCount());
        threadPoolExecutor.shutdown();
    }

}
