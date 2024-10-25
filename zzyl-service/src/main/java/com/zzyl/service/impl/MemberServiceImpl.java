package com.zzyl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.zzyl.constant.Constants;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.entity.Member;
import com.zzyl.mapper.MemberMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.service.MemberService;
import com.zzyl.service.WechatService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/23
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private WechatService wechatService;
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    static ArrayList DEFAULT_NICKNAME_PREFIX = Lists.newArrayList(
            "生活更美好",
            "大桔大利",
            "日富一日",
            "好柿开花",
            "柿柿如意",
            "一椰暴富",
            "大柚所为",
            "杨梅吐气",
            "天生荔枝"
    );

    @Override
    public LoginVo login(UserLoginRequestDto userLoginRequestDto) {
        //1.调用微信api,根据code获取openId
        String openId = wechatService.getOpenid(userLoginRequestDto.getCode());
        //2.根据openId查询用户
        Member member = memberMapper.getByOpenId(openId);
        //3.如果用户为空，则新增
        if (ObjectUtil.isEmpty(member)) {
            member = Member.builder().openId(openId).build();
        }
        //4.调用微信api获取用户绑定的手机号
        String phone = wechatService.getPhone(userLoginRequestDto.getPhoneCode());
        //5.保存或修改用户
        saveOrUpdate(member, phone);
        //6.将用户id存入token,返回
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.JWT_USERID, member.getId());
        claims.put(Constants.JWT_USERNAME, member.getName());
        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey()
                , jwtTokenManagerProperties.getTtl()
                , claims);
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setNickName(member.getName());
        return loginVo;
    }

    private void saveOrUpdate(Member member, String phone) {
        //1.判断取到的手机号与数据库中保存的手机号是否一样
        if (ObjectUtil.notEqual(phone, member.getPhone())) {
            //设置手机号
            member.setPhone(phone);
        }
        //2.判断id存在
        if (ObjectUtil.isNotEmpty(member.getId())) {
            memberMapper.update(member);
            return;
        }
        //3.保存新的用户
        //随机组装昵称，词组+手机号后四位
        String nickName = DEFAULT_NICKNAME_PREFIX.get((int) (Math.random() * DEFAULT_NICKNAME_PREFIX.size()))
                + StringUtils.substring(member.getPhone(), 7);

        member.setName(nickName);
        memberMapper.save(member);
    }
}
