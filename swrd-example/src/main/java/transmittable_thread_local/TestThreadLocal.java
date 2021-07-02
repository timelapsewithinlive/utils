package transmittable_thread_local;

/**
 * @author xinghonglin
 * @date 2021/06/16
 */
public class TestThreadLocal {

    static InheritableThreadLocal localsss = new InheritableThreadLocal(){
        @Override
        protected Object initialValue() {
            return "main线程";
        }
    };
    public static void main(String[] args) {
        System.out.println(localsss.get());
        localsss.set("传递值");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = localsss.get();
                System.out.println(o);
            }
        });
        thread.start();
    }

}
