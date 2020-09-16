package com.swrd.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * check health
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String checkHealth() {
        return "ok";
    }
}