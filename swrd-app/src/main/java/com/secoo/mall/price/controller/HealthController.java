package com.secoo.mall.price.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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

    }
}