package com.walter.spbt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walter.spbt.bean.SpbtResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = {"SM-国密SM算法相关接口"})
@RequestMapping("sm")
public class SmController {
    @GetMapping("sm3")
    @ApiOperation(value = "获取SM3杂凑值",response = SpbtResponseEntity.class)
    public SpbtResponseEntity getSm3Res(@ApiParam(value = "杂凑秘钥") @RequestParam String key){
        return new SpbtResponseEntity();
    }
}
