package com.group2.cms.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.group2.cms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @date 2025-12-30
 * @description TODO
 */
@RestController
public class AIController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/auth/ai")
    public Result chatByAI(String message) throws JsonProcessingException {
        //访问url
        String url = "http://localhost:8899/ai?msg="+message;
        String content = restTemplate.getForObject(url, String.class);
        return Result.success("操作成功",content);
    }
}
