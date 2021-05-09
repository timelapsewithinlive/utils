package jdk8.functions;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static jdk8.functions.Functions.f;
import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * @Author honglin.xhl
 * @Date 2020/9/23 3:58 下午
 */
public class Test {
    private String msg;

    public static void main(String[] args) throws Throwable {
        System.out.println(System.currentTimeMillis());
        String rejectWords = "{\"1\":\"酒店酷讯,酒店 酷讯,kuxun酒店,kuxun,酷讯比价网,酷讯电话,酷讯网电话,酷讯返现,酷讯网酒店预定,酷讯酒店预定,酷讯网宾馆预定,酷讯列车,酷讯 订酒店,酷讯特价酒店预订,北京酒店预订酷讯,酷讯网酒店预订,酷讯网订宾馆,酷讯订房,酷讯网宾馆预订,酷讯订酒店,酷讯网定酒店,酷讯网订酒店,酷讯酒店预订,酷讯酒店怎么样,酷讯旅游怎么样,酷讯网怎么样,酷讯旅游网怎么样,酷讯网酒店点评,酷讯旅游安全吗,酷讯旅游可靠吗,酷讯网可靠吗,酷讯旅游网可靠吗,酷讯旅游好不好\"}";
        Map map = JSONObject.parseObject(rejectWords, Map.class);
        System.out.println(map);

        char autoAuditState = "11121".charAt(3);
        System.out.println(Integer.valueOf(autoAuditState+"") == 2);

        System.out.println(Integer.toOctalString(4681));
        System.out.println("11121".charAt(3));

        Test test = new Test();
        //f(test).whenDefault(Test::get, false, (f, p) -> f.setMsg(p.getMsg()));
        System.out.println(test);

        CheckedConsumer<Object> of = CheckedConsumer.of((x) -> { });
        Consumer<Object> unchecked = of.unchecked();
        unchecked.accept(333);
        System.out.println(of);

        CheckSupplier<Object> checkSupplier = CheckSupplier.of(() -> {
            throw new RuntimeException("哈哈");
        });
        Supplier<Object> unchecked1 = checkSupplier.unchecked();
        unchecked1.get();
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
