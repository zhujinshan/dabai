package com.dabai.proxy.config.token;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.result.ResultCode;
import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/9 23:23
 */
@Slf4j
@Component
public class TokenAuthInterceptor extends HandlerInterceptorAdapter {

    private static final String TOKEN_KEY = "access_token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(CheckToken.class)) {
            return true;
        }
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            authFailed(response);
            return false;
        }

        if (!JwtTools.checkToken(token)) {
            authFailed(response);
            return false;
        }

        String sessionKey = JwtTools.getSessionKey(token);
        String openId = JwtTools.getOpenId(token);
        log.info("userSession: sessionKey={}, openId;{}", sessionKey, openId);

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(openId)) {
            authFailed(response);
            return false;
        }
        UserSessionInfo sessionInfo = new UserSessionInfo(openId, sessionKey);
        UserSessionContext.setSessionInfo(sessionInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserSessionContext.clear();
    }

    public void authFailed(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Result<String> result = Result.genResult(ResultCode.UN_LOGIN.getValue(), ResultCode.UN_LOGIN.getReason(), null);
        response.getWriter().write(JsonUtils.toJson(result));
    }
}
