package ciglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {

    // CGLib需要代理的目标对象
    private Object targetObject;

    public Object bind(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        // 设置父类--可以是类或者接口
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        // 返回代理对象
        return proxyObj;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        Object obj = null;
        //模拟功能增强
        HumanUtil humanUtil = new HumanUtil();
        humanUtil.method1();
        // 执行目标目标对象方法
        obj = method.invoke(targetObject, args);
        //模拟功能增强
        humanUtil.method2();
        return obj;
    }

}
