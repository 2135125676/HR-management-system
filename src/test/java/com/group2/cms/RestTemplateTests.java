package com.group2.cms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2025-12-30
 * @description 实现网络请求和响应操作
 */
@SpringBootTest
public class RestTemplateTests {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void method(){
        //访问远程服务器： www.baidu.com
        String url = "http://www.baidu.com";

        String responseBody = restTemplate.getForObject(url, String.class);

        System.out.println(responseBody);
    }
    @Test
    public void getMessage() throws JsonProcessingException {
        //访问url
        String url = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";
        //构建请求信息对象
        HttpHeaders headers = new HttpHeaders();
        //-H "Content-Type: application/json"
        headers.setContentType(MediaType.APPLICATION_JSON);
        //-H "Authorization: Bearer $ARK_API_KEY"
        headers.set("Authorization","Bearer 0e7304fc-220d-471f-9b9ax-e2a8fff51196");
        //请求体
        String requestBody = """
                {
                     "model": "doubao-seed-1-6-lite-251015",
                     "max_completion_tokens": 65535,
                     "messages": [
                        {
                            "content": [
                                {
                                    "text": "你是谁?",
                                    "type": "text"
                                }
                            ],
                            "role": "user"
                        }
                    ],
                     "reasoning_effort": "medium"
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String responseBody = restTemplate.postForObject(url, request, String.class);
        Map map = objectMapper.readValue(responseBody, Map.class);
        List<Map> choicesList  = (List<Map>) map.get("choices");
        Map message = (Map) choicesList.get(0).get("message");
        String content = message.get("content").toString();
        System.out.println("AI回复内容："+content);
    }
    @Test
    public void testFormmat(){
        String requestBody = """
                {
                     "model": "doubao-seed-1-6-lite-251015",
                     "max_completion_tokens": 65535,
                     "messages": [
                        {
                            "content": [
                                {
                                    "text": "%s",
                                    "type": "text"
                                }
                            ],
                            "role": "user"
                        }
                    ],
                     "reasoning_effort": "medium"
                }
                """;
        String message = "你是谁？";
        String format = String.format(requestBody,message);
        System.out.println(format);
    }
}
