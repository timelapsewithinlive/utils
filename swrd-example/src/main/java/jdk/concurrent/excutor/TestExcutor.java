package jdk.concurrent.excutor;

import java.util.concurrent.*;

public class TestExcutor {

    static ExecutorService executor = Executors.newFixedThreadPool(3);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,60000, TimeUnit.MILLISECONDS,new SynchronousQueue<>(),new RejectedExecutionHandler() {
        //线程池满时用当前线程处理任务
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("线程池满，走拒绝策略");
        }
    });

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        while (true){
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    //Thread.sleep(5000);
                    System.out.println("线程执行");
                    return "返回值";
                }
            };
            test(callable);
        }

        //Future<String> submit = executor.submit(callable);

        //threadPoolExecutor.shutdown();
    }

    public static void test (Callable callable) throws ExecutionException, InterruptedException {
        Future<String> submit =threadPoolExecutor.submit(callable);
        //System.out.println(submit.get());
        //System.out.println("activethread:"+threadPoolExecutor.getActiveCount());
        //threadPoolExecutor.shutdown();


    }

}
