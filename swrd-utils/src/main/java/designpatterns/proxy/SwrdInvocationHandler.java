package designpatterns.proxy;

import java.lang.reflect.Method;

public interface SwrdInvocationHandler {

    /**
     * 代理类对业务增强时需要实现该方法，动态代理最终调用的是该方法的实现
     *
     * @param proxy  生成的代理类
     * @param method 代理的方法
     * @param args   代理的方法形参
     * @return 返回代理执行后的结果
     */
    Object invoke(Object proxy, Method method, Object[] args);
}
