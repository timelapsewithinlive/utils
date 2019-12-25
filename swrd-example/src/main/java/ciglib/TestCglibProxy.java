package ciglib;

import org.springframework.cglib.core.DebuggingClassWriter;

public class TestCglibProxy {

    public static void main(String[] args) {
        //创建一个被代理类的对象
        SuperMan man = new SuperMan();

        // 添加如下代码，获取代理类源文件
        String path = CGLibProxy.class.getResource(".").getPath();
        System.out.println(path);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, path);



        CGLibProxy cgLibProxy = new CGLibProxy();
        //返回一个代理类的对象--注意这里现在传入的是实现类
        Object obj = cgLibProxy.bind(man);
        System.out.println(obj.getClass());
        //class com.web.test.SuperMan$$EnhancerByCGLIB$$3be74240
        Human hu = (Human)obj;
        //通过代理类的对象调用重写的抽象方法
        hu.info();

        System.out.println();

        hu.fly();
    }

}
