package designpatterns.chain;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class HandlerAop {

    @Pointcut("@annotation(designpatterns.chain.ChainTransactional)")
    public void transactionPointcut() {};

    @Before("transactionPointcut()")
    public void transactionBefore(JoinPoint joinPoint) throws InterruptedException {
        System.out.println("事物方法开始通过aop校验前方异步handler是否执行完成");
        Object[] args = joinPoint.getArgs();
        ((Request)args[0]).countDownLatch.await();
    }

    @Pointcut("@annotation(designpatterns.chain.UnNecessary)")
    public void unNecessaryPointcut() {};

    @Before("unNecessaryPointcut()")
    public void unNecessaryBefore(JoinPoint joinPoint) throws InterruptedException {
        System.out.println("不必要等待的异步handler直接将计数器减1");
        Object[] args = joinPoint.getArgs();
        //((Request)args[0]).countDownLatch.countDown();
    }

   /* @Before("designpatterns.chain.SynHandler.synHandle()")
    public void before(){

    }

    @After("annotation PointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Mylog mylog = method.getAnnotation(Mylog.class);
        System.out.println("日志等级"+ mylog.level()+ "注解式拦截");
    }

    @Around("point()")
    public Object doAround(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println(methodName + "方法执行了" + (endTime - startTime) + "ms");
        return obj;

    }*/

}
