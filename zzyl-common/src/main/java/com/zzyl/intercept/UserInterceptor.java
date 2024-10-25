package com.zzyl.intercept;

import cn.hutool.core.map.MapUtil;
import com.zzyl.constant.Constants;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法就放行，比如跨域验证请求、静态资源等不需要身份校验的请求
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //获取header的参数
        String token = request.getHeader(Constants.USER_TOKEN);
        log.info("开始解析 customer user token:{}",token);
        if(ObjectUtil.isEmpty(token)){
            //token失效
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        Map<String,Object> claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token);
        if (ObjectUtil.isEmpty(claims)) {
            //token失效
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        //获取用户ID
        Long userId = MapUtil.get(claims, Constants.JWT_USERID, Long.class);
        if (ObjectUtil.isEmpty(userId)) {
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        //存入当前请求的线程中
        UserThreadLocal.set(userId);
        //以上检查都没问题，一定返回true，这个请求才能继续
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //响应结束，需清理ThreadLocal中的数据，防止内存泄漏
        UserThreadLocal.remove();
    }
}