package com.walter.spbt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(tags="swagger集成测试，后续作为业务开发")
public class HelloWorldController {
    @RequestMapping("/sayhello")
    public String sayHello(){
        return "static/index";
    }
}
