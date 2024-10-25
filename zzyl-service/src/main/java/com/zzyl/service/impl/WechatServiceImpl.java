package com.zzyl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzyl.service.WechatService;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/23
 */
@Service
public class WechatServiceImpl implements WechatService {
    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";
    @Value("${zzyl.wechat.appId}")
    private String appId;

    @Value("${zzyl.wechat.appSecret}")
    private String secret;
    /*微信登录接口（code2session）：
    当登录成功时，不会返回 errcode。在成功的情况下，返回的字段包括 openid、session_key 和可能的 unionid。
    如果发生错误，比如 js_code 无效或其他错误，才会返回 errcode 和 errmsg。典型的错误码包括 40029（无效的 code）或 45011（
    API 调用频率限制）。*/
    @Override
    public String getOpenid(String code) {
        //封装参数
        Map<String,Object> requestUrlParam = getAppConfig();
        requestUrlParam.put("js_code",code);
        //发起请求
        //get请求可以直接传输map，请求的库会自动将这些参数拼接到 URL中
        String result = HttpUtil.get(REQUEST_URL, requestUrlParam);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        // 若code不正确，则获取不到openid，响应失败
        if (jsonObject.getInt("errcode") != 0) {
            throw new RuntimeException(jsonObject.getStr("errmsg"));
        }
        return jsonObject.getStr("openid");
    }
/*    微信获取手机号接口：
    不管成功与否，总是会返回 errcode，成功时 errcode 为 0，失败时 errcode 是其他错误代码，并伴有 errmsg 字段。*/
    @Override
    public String getPhone(String code) {
        //获取access_token
        String token = getToken();
        //拼接请求路径
        String url = PHONE_REQUEST_URL + token;

        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //post请求需要json格式传递
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(param));
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (jsonObject.getInt("errcode") != 0) {
            //若code不正确，则获取不到phone，响应失败
            throw new RuntimeException(jsonObject.getStr("errmsg"));

        }
        return jsonObject.getJSONObject("phone_info").getStr("purePhoneNumber");
    }
    public String getToken(){
        Map<String, Object> requestUrlParam = getAppConfig();

        String result = HttpUtil.get(TOKEN_URL, requestUrlParam);
        //解析
        JSONObject jsonObject = JSONUtil.parseObj(result);
        //如果code不正确，则失败
        if(ObjectUtil.isNotEmpty(jsonObject.getInt("errcode"))){
            throw new RuntimeException(jsonObject.getStr("errmsg"));
        }
        return jsonObject.getStr("access_token");

    }

    /**
     * 封装公共参数
     * @return
     */
    private Map<String, Object> getAppConfig() {
        Map<String, Object> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid",appId);
        requestUrlParam.put("secret",secret);
        return requestUrlParam;
    }
}
