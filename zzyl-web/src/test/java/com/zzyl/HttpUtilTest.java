package com.zzyl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import org.apache.catalina.manager.HTMLManagerServlet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/23
 */
@SpringBootTest
public class HttpUtilTest {
    @Test
    public void testGet() {
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        String result = HttpUtil.get("https://www.baidu.com");
        System.out.println(result);
    }
    @Test
    public void testGetByParam() {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 10);
        String result = HttpUtil.get("http://localhost:9995/nursing_project", paramMap);
        System.out.println(result);
    }
    @Test
    public void testCreateRequest() {
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 10);
        HttpResponse response = HttpUtil.createRequest(Method.GET, url)
                .form(paramMap)
                .execute();
        if (response.getStatus() == 200) {
            System.out.println(response.body());
        }
    }
    @Test
    public void testTianqi(){
        String url = "https://ali-weather.showapi.com/hour24";
        String appcode = "e4e736712452478bb09313e5f6838910";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("area", "广州");
        HttpResponse authorization = HttpUtil.createRequest(Method.GET, url)
                .header("Authorization", "APPCODE " + appcode)
                .form(param)
                .execute();
        if (authorization.getStatus() == 200) {
            System.out.println(authorization.body());
        }
    }
}
