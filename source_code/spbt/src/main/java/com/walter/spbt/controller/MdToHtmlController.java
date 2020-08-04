package com.walter.spbt.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = {"Markdown-markdown转html"})
@RequestMapping("markdown")
@Slf4j
public class MdToHtmlController {
    @GetMapping("mdfile")
    @ResponseBody
    public void getMarkdownFile(HttpServletResponse response) {
        File file = new File("src/main/resources/static/files/前端性能优化-了解浏览器背后的运行机制.md");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getOutputStream().write(buffer);
        } catch (Exception e) {
            log.error("文件流处理异常", e);
        }
    }
}
