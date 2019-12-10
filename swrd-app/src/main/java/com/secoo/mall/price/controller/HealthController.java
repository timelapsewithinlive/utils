package com.secoo.mall.price.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * check health
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@RestController
public class HealthController {
    @GetMapping("/health")
    public String checkHealth() {
        return "ok";
    }
}