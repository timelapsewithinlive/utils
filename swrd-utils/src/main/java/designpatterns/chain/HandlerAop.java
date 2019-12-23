package designpatterns.chain;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class HandlerAop {

   /* @Pointcut("@annotation(powerx.io.Mylog)")
    public void annotationPointCut() {};

    @Before("designpatterns.chain.SynHandler.synHandle()")
    public void transactionBefore(){

    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Mylog mylog = method.getAnnotation(Mylog.class);
        System.out.println("日志等级"+ mylog.level()+ "注解式拦截");
    }*/


}
