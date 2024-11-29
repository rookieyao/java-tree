package com.rookie.java.tree.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author rookie
 * @Date 2024-11-27 23:31
 * @Description
 **/
@RestController
public class NacosController {

    @Value("${test.config.name}")
    private String name;

    @GetMapping("/getConfig")
    public String getConfig(){
        return name;
    }
}
