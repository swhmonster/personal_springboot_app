package com.walter.spbt.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ResponseEntity", description = "请求响应体")
public class SpbtResponseEntity {
    @JsonProperty("code")
    @ApiModelProperty(value = "状态码")
    private int code;
    @JsonProperty("message")
    @ApiModelProperty(value = "信息")
    private String message;
    @JsonProperty("data")
    @ApiModelProperty(value = "数据")
    private Object data;
}
