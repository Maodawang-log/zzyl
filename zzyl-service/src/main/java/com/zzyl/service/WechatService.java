package com.zzyl.service;

import cn.hutool.json.JSONObject;

import java.io.IOException;

/**
 * @author sjqn
 */
public interface WechatService {

    /**
     * 获取openid
     * @param code  登录凭证
     * @return
     * @throws IOException
     */
    public String getOpenid(String code) ;

    /**
     * 获取手机号
     * @param code  手机号凭证
     * @return
     * @throws IOException
     */
    public String getPhone(String code);

}