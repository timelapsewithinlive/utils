package com.secoo.mall.price.controller;

import designpatterns.chain.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Controller
public class OrderController {
    @Resource
    OrderService orderService;

    @PostConstruct
    public void init(){
        System.out.println(5555555555555l);
    }

    @RequestMapping("/createOrder")
    public Object createOrder(){

        return orderService.mockedCreateOrder(0);
    }
}
