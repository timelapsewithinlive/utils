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

    @GetMapping("/createOrder")
    public Object createOrder(){

        return orderService.mockedCreateOrder(0);
    }
}
