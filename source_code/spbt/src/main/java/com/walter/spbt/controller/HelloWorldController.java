package com.walter.spbt.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(tags="swagger集成测试，后续作为业务开发")
@RequestMapping("api")
public class HelloWorldController {
    @GetMapping("sayhello")
    @ApiOperation(value = "测试",response = String.class)
    public String sayHello(){
        return "static/index";
    }
}
