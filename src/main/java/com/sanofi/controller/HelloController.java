package com.sanofi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanofi.service.HelloService;


@RestController
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/test-conn")
    public String index() {
        return this.helloService.testConn();
    }

    @GetMapping("/")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

}
