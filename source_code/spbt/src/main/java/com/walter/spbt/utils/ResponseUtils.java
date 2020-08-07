package com.walter.spbt.utils;

import org.springframework.http.HttpStatus;

import com.walter.spbt.bean.SpbtResponseEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtils {
    /**
     * 成功响应
     *
     * @param object 响应数据
     * @return SpbtResponseEntity
     */
    public SpbtResponseEntity successResponse(Object object) {
        return SpbtResponseEntity.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(object)
                .build();
    }

    /**
     * 失败响应
     *
     * @param object 响应数据
     * @return SpbtResponseEntity
     */
    public SpbtResponseEntity failureResponse(Object object) {
        return SpbtResponseEntity.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .data(object)
                .build();
    }
}
