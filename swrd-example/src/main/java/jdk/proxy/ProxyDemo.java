package jdk.proxy;

import ciglib.Human;
import ciglib.SuperMan;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {


    static Object getProxy(Object obj,Class[] classes){
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), classes, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("打印的地方就是JDK增强的点");
                method.invoke(obj, args);
                return proxy;
            }
        });

    }

    public static void main(String[] args) throws IOException {
        SuperMan superMan = new SuperMan();
        Class[] classes = {Human.class};
        Human proxy = (Human) ProxyDemo.getProxy(superMan, classes);
        proxy.fly();

        /**
         * 上面生成的代理对象字节码 com.sun.proxy.$Proxy0 是在内存中的
         * 这里将其对象写到文件中，通过反编译查看
         */
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", classes);
        FileOutputStream fos = new FileOutputStream("E://$Proxy0.class");
        fos.write(bytes);
        fos.flush();
        System.out.println("文件写入成功");

    }
}
