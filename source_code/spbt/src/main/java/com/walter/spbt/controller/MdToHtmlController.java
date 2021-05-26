package com.walter.spbt.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.walter.spbt.bean.SpbtResponseEntity;
import com.walter.spbt.utils.ResponseUtils;

import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = {"Markdown-markdown转html"})
@RequestMapping("markdown")
@Slf4j
public class MdToHtmlController {

    @Value("${markdown.filepath:/home/spbt/mdfiles/}")
    private String mdFilePath;

    private static final String FILE_PATH = "static/vuepress/mdfiles/";
    private static final String FILE_NAME = "fileName";
    private static final String FILE_ADRESS = "fileAddress";

    @SneakyThrows
    @GetMapping("mdfile")
    @ResponseBody
    public SpbtResponseEntity getMarkdownFile() {
        File file = new File(mdFilePath);
        List<File> fileList = Arrays.asList(Objects.requireNonNull(file.listFiles()));
        fileList.sort(Comparator.comparing(File::getName));
        List<Map<String, String>> linkList = new ArrayList<>();
        fileList.forEach(e -> {
            Map<String, String> map = new HashMap<>();
            map.put(FILE_NAME, e.getName());
            map.put(FILE_ADRESS, FILE_PATH + e.getName());
            linkList.add(map);
        });
        return ResponseUtils.successResponse(linkList);
    }
}
