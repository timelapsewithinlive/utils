package designpatterns.chain;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class HandlerAop {

    @Pointcut("@annotation(designpatterns.chain.ChainTransactional)")
    public void transactionPointcut() {};

    @Around("transactionPointcut()")
    public Object transactionAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("事物方法开始通过aop校验前方异步handler是否执行完成");
        Object[] args = joinPoint.getArgs();
        Request request = (Request) args[0];
        //request.countDownLatch.await(request.TransactionWaitTimeOut, TimeUnit.SECONDS);
        request.countDownLatch.await();
        boolean b = request.isPropagation.get();
        if(b){
            return joinPoint.proceed(args);
        }else{
            System.out.println("事物方法执行前,有失败的异步节点。直接返回，寻找失败节点中的原因");
            return new Response(HandlerCurrentlyStatus.SUCCESS,null);
        }
    }

    @Pointcut("execution(* designpatterns.chain.AsynHandler.asynHandle*(..))")
    public void unNecessaryPointcut() {};

    //非降级的异步handler处理完成后必须将计数器减1
    @Around("unNecessaryPointcut()")
    public Object unNecessaryAround(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature ms = (MethodSignature)pjp.getSignature();
        Method method = ms.getMethod();
        Object[] args = pjp.getArgs();
        Request request = ((Request)args[0]);

        UnNecessary annotation = AnnotationUtils.findAnnotation(method,UnNecessary.class);

        //业务放行
        Response response = (Response)pjp.proceed(args);

        if(HandlerCurrentlyStatus.FAIL.equals(response.getFlag())){
            request.isPropagation.compareAndSet(true,false);
        }

        if(annotation==null){
            System.out.println(method.getDeclaringClass().getName()+" 必要等待的异步handler执行完成后将计数器减1");
            request.countDownLatch.countDown();
        }
        return response;
    }

    //异步handler执行出现异常后，也必须将计数器减1
    @AfterThrowing("unNecessaryPointcut()")
    public void unNecessaryAfterThrowing(JoinPoint pjp) throws Throwable {
        MethodSignature ms = (MethodSignature)pjp.getSignature();
        Method method = ms.getMethod();

        UnNecessary annotation = AnnotationUtils.findAnnotation(method,UnNecessary.class);
        if(annotation==null){
            Object[] args = pjp.getArgs();
            Request request = ((Request)args[0]);
            request.isPropagation.compareAndSet(true,false);
            request.countDownLatch.countDown();
            System.out.println(method.getDeclaringClass().getName()+" 异步handler出现异常将全局状态变更后,将计数器减1");
        }
    }

}
