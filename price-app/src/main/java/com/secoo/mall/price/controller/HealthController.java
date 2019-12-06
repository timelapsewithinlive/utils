package com.secoo.mall.price.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * check health
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@Slf4j
@RestController
public class HealthController {
    @GetMapping("/health")
    public String checkHealth() {
        return "ok";
    }
}