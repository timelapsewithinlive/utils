package com.secoo.mall.price.controller;

import designpatterns.tools.AbstractHandler;
import designpatterns.DecadeInventoryHandler;
import designpatterns.tools.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

/**
 * check health
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@RestController
public class HealthController {
    @PostConstruct
    public void init(){
        System.out.println(5555555555555l);
    }
    @GetMapping("/health")
    public String checkHealth() {
        return "ok";
    }

    public static void  main(String[] args){
        try{
            AbstractHandler.Task task = new AbstractHandler.Task(new DecadeInventoryHandler(), new Request());
            Future submit = AbstractHandler.threadPoolExecutor.submit(task);
            submit.get();
          /*  FutureTask futureTask = new FutureTask(task);
            futureTask.run();
            futureTask.get();*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}