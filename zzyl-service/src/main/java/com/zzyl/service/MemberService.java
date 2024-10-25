package com.zzyl.service;

import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.vo.LoginVo;

import java.io.IOException;

/**
 * @author sjqn
 */
public interface MemberService {


    /**
     * 小程序端登录
     * @param userLoginRequestDto
     * @return
     */
    LoginVo login(UserLoginRequestDto userLoginRequestDto);
}