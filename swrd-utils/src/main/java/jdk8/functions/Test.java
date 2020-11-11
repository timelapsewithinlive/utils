package jdk8.functions;

import java.util.function.Consumer;

import static jdk8.functions.Functions.f;

/**
 * @Author honglin.xhl
 * @Date 2020/9/23 3:58 下午
 */
public class Test {
    private String msg;

    public static void main(String[] args) throws Throwable {
        Test test = new Test();
        //f(test).whenDefault(Test::get, false, (f, p) -> f.setMsg(p.getMsg()));
        System.out.println(test);

        CheckedConsumer<Object> of = CheckedConsumer.of((x) -> { });
        Consumer<Object> unchecked = of.unchecked();
        unchecked.accept(333);
        System.out.println(of);
    }

    public static Test get(Test test) {
        return test;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Test{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
