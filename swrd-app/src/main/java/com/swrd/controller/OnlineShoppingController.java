package com.swrd.controller;

import designpatterns.chain.Request;
import designpatterns.chain.test.OrderSubmitService;
import designpatterns.facede.Facede;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.ApplicationContextHolder;

import javax.annotation.Resource;
import java.util.Map;

//启动后访问链接：http://127.0.0.1:8080/api?busiType=trade&service=createOrder&orderSource=app
@RestController
public class OnlineShoppingController{
    @Resource
    OrderSubmitService orderSubmitService;

    @GetMapping("/api")
    public Object trade(@RequestParam Map<String,Object> params){
        String busiType = params.get("busiType")+"";
        Facede facede = (Facede)ApplicationContextHolder.getBean(busiType + "Facede");
        Request request = new Request(params);
        facede.guide( request);
        return "OK";
    }
}
