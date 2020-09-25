package jdk8.functions;

import static jdk8.functions.Func.f;

/**
 * @Author honglin.xhl
 * @Date 2020/9/23 3:58 下午
 */
public class Test {
    private String msg;

    public static void main(String[] args) {
        Test test = new Test();
        f(test).whenDefault(Test::get, false, (f, p) -> f.setMsg(p.getMsg()));
        System.out.println(test);
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
