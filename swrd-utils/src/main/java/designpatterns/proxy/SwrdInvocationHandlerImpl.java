package designpatterns.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SwrdInvocationHandlerImpl implements SwrdInvocationHandler {
    private Object target;

    /**
     * 获取代理对象
     *
     * @param object 被代理对象
     * @return 返回代理类
     */
    public Object getInstance(Object object) {
        try {
            this.target = object;
            return SwrdDynamicProxy.newProxyInstance(new SwrdClassLoader(), object.getClass().getInterfaces(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代理执行前后的业务逻辑，该方法由生成的代理类调用
     *
     * @param proxy  代理对象
     * @param method 代理执行的方法
     * @param args   代理执行的方法形参
     * @return 返回代理方法执行的结果，返回的Object对象由生成的代理类根据代理方法的返回值进行强转
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            System.out.println("swrd动态代理增强开始执行啦...");
            Object invoke = null;
            invoke = method.invoke(this.target, args);
            System.out.println("swrd动态代理增强执行结束啦...");
            return invoke;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
