package com.dabai.proxy.config.security;

import com.dabai.proxy.config.AdminUserSessionContext;
import com.dabai.proxy.config.AdminUserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.result.ResultCode;
import com.dabai.proxy.dao.SysAdminMapper;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.enums.SysAdminStatus;
import com.dabai.proxy.po.SysAdmin;
import com.dabai.proxy.utils.JsonUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/3 16:03
 */
@Slf4j
@Component
public class UserSecurityInterceptor extends HandlerInterceptorAdapter implements EnvironmentAware {

    private static final String TOKEN_KEY = "access_token";

    @Resource
    private SysAdminMapper sysAdminMapper;

    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        PathRole pathRole = method.getAnnotation(PathRole.class);
        boolean needLogin = false;
        SysAdminRole userRole = SysAdminRole.NORMAL_USER;
        if (Objects.nonNull(pathRole)) {
            needLogin = pathRole.needLogin();
            userRole = pathRole.role();
        }

        if(!needLogin) {
            return true;
        }

        String token = request.getHeader(TOKEN_KEY);
        List<String> activeProfiles = Lists.newArrayList(environment.getActiveProfiles());
        if (StringUtils.isEmpty(token) && activeProfiles.contains("test")) {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJEYUJhaUFkbWluV2l0aEpXVCIsInVzZXJJZCI6IjE4In0.9df8WF-kfG2V0DTjgxDsRhi4w5-13dXgV1GYPfwyn8E";
        }

        if (StringUtils.isEmpty(token)) {
            unLogin(response);
            return false;
        }

        if (!JwtTools.checkToken(token)) {
            unLogin(response);
            return false;
        }

        // todo:checkToken 是否过期
        String userId = JwtTools.getUserId(token);
        AdminUserSessionInfo admin = getAdmin(Long.parseLong(userId));
        if (Objects.isNull(admin)) {
            unLogin(response);
            return false;
        }

        SysAdminRole currentUserRole = admin.getRole();
        if (Objects.isNull(currentUserRole) || currentUserRole.getWeight() < userRole.getWeight()) {
            unAuth(response);
            return false;
        }
        log.info("current login admin:{}", admin);
        AdminUserSessionContext.setAdminUserSessionInfo(admin);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdminUserSessionContext.clear();
    }

    private AdminUserSessionInfo getAdmin(Long userId) {
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(sysAdmin) || sysAdmin.getStatus().equals(SysAdminStatus.DISABLE.getCode()) || sysAdmin.getStatus().equals(SysAdminStatus.NOPASS.getCode())) {
            return null;
        }
        AdminUserSessionInfo adminUserSessionInfo = new AdminUserSessionInfo();
        adminUserSessionInfo.setUserId(sysAdmin.getId());
        adminUserSessionInfo.setMobile(sysAdmin.getMobile());
        adminUserSessionInfo.setRole(SysAdminRole.getRoleByCode(sysAdmin.getRole()));
        String organizationCode = sysAdmin.getOrganizationCode();
        if(StringUtils.isNotEmpty(organizationCode)) {
            List<String> organizations = Arrays.stream(organizationCode.split(",")).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            adminUserSessionInfo.setOrganizationCodes(organizations);
        }
        adminUserSessionInfo.setCharge(sysAdmin.getCanCharge() == 1);
        String modules = sysAdmin.getModules();
        if (StringUtils.isNotEmpty(modules)) {
            List<Integer> moduleList = Arrays.stream(modules.split(",")).filter(StringUtils::isNotEmpty).map(Integer::parseInt).collect(Collectors.toList());
            adminUserSessionInfo.setModules(moduleList);
        }
        adminUserSessionInfo.setName(sysAdmin.getName());
        return adminUserSessionInfo;
    }


    private void unAuth(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Result<String> result = Result.genResult(ResultCode.CAN_NOT_VISIT.getValue(), ResultCode.CAN_NOT_VISIT.getReason(), null);
        response.getWriter().write(JsonUtils.toJson(result));
    }

    public void unLogin(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Result<String> result = Result.genResult(ResultCode.UN_LOGIN.getValue(), ResultCode.UN_LOGIN.getReason(), null);
        response.getWriter().write(JsonUtils.toJson(result));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
