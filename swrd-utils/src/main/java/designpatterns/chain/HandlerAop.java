package designpatterns.chain;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class HandlerAop {

    @Pointcut("@annotation(designpatterns.chain.ChainTransactional)")
    public void transactionPointcut() {};

    @Before("transactionPointcut()")
    public void transactionBefore(JoinPoint joinPoint) throws InterruptedException {
        System.out.println("事物方法开始通过aop校验前方异步handler是否执行完成");
        Object[] args = joinPoint.getArgs();
        Request request = (Request) args[0];
        request.countDownLatch.await(request.TransactionWaitTimeOut, TimeUnit.SECONDS);
    }

    @Pointcut("execution(* designpatterns.chain.AsynHandler.asynHandle*(..))")
    public void unNecessaryPointcut() {};

    @Around("unNecessaryPointcut()")
    public void unNecessaryBefore(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature ms = (MethodSignature)pjp.getSignature();
        Method method = ms.getMethod();
        Object[] args = pjp.getArgs();
        Request request = ((Request)args[0]);

        UnNecessary annotation = AnnotationUtils.findAnnotation(method,UnNecessary.class);
        if(annotation!=null){
            System.out.println("不必要等待的异步handler直接将计数器减1");
            request.countDownLatch.countDown();
        }

        //业务放行
        pjp.proceed(args);

        if(annotation==null){
            System.out.println("必要等待的异步handler执行完成后将计数器减1");
            request.countDownLatch.countDown();
        }

    }

}
