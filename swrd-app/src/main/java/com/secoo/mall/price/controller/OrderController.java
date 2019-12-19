package com.secoo.mall.price.controller;

import designpatterns.chain.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    OrderService orderService;

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

    @GetMapping("/createOrder")
    public Object createOrder(){

        return orderService.mockedCreateOrder(0);
    }
}
