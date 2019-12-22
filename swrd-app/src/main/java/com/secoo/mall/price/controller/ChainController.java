package com.secoo.mall.price.controller;

import designpatterns.chain.Handler;
import designpatterns.chain.HandlerContext;
import designpatterns.chain.OrderService;
import designpatterns.chain.Pipeline;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
public class ChainController implements  ApplicationContextAware {
    @Resource
    OrderService orderService;

    private ApplicationContext context;

    @PostConstruct
    public void init(){
        for (int i=0;i<10;++i){
           Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        orderService.mockedCreateOrder(0);
                    }
                }
            });
            thread.start();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @GetMapping("/chain")
    public Object createOrder(){

        return orderService.mockedCreateOrder(0);
    }
}
