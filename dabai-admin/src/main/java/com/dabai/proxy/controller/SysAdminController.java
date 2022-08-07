package com.dabai.proxy.controller;

import cn.hutool.core.util.PhoneUtil;
import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.AdminUserSessionContext;
import com.dabai.proxy.config.AdminUserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.JwtTools;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.enums.ModuleEnum;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.enums.SysAdminStatus;
import com.dabai.proxy.httpclient.tencentcloud.TencentSmsClient;
import com.dabai.proxy.po.SysAdmin;
import com.dabai.proxy.query.SysAdminQuery;
import com.dabai.proxy.req.Paging;
import com.dabai.proxy.req.SysAdminLoginReq;
import com.dabai.proxy.resp.SysAdminDTO;
import com.dabai.proxy.resp.SysAdminPageResp;
import com.dabai.proxy.service.SysAdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/5 23:02
 */
@RestController
@RequestMapping("/sysAdmin")
@Api(tags = "管理员接口")
@Slf4j
public class SysAdminController {

    @Autowired
    private TencentSmsClient tencentSmsClient;
    @Autowired
    private SysAdminService sysAdminService;

    @GetMapping(value = "/sendSubmitCode")
    @ApiOperation(value = "发送短信验证码", httpMethod = "GET")
    public Result<Boolean> sendSubmitCode(@RequestParam @ApiParam(value = "手机号", required = true) String mobile) {
        log.info("下发短信验证码：mobile：{}", mobile);
        if (LocalCache.contains(mobile)) {
            return Result.genResult(-1, "验证码还在有效期，请不要频繁请求", false);
        }
        // todo: 更换短信模板
        tencentSmsClient.sendSubmitVerificationCode(mobile);
        return Result.success(true);
    }


    @PostMapping(value = "/login")
    @ApiOperation(value = "登录", httpMethod = "POST")
    public Result<String> login(@ApiParam(value = "登录入参", required = true) @RequestBody SysAdminLoginReq sysAdminLoginReq) {
        log.info("login：sysAdminLoginReq：{}", sysAdminLoginReq);
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminLoginReq.getMobile()), "手机号不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminLoginReq.getCode()), "验证码不能为空");
        Assert.isTrue(LocalCache.checkCode(sysAdminLoginReq.getMobile(), sysAdminLoginReq.getCode()), "验证码无效，请重新获取");

        SysAdmin sysAdmin = sysAdminService.getByMobile(sysAdminLoginReq.getMobile());
        Assert.notNull(sysAdmin, "无效账号，请重新输入手机号");
        String token = JwtTools.generateToken(sysAdmin.getId());
        return Result.success(token);
    }

    @GetMapping(value = "/info")
    @ApiOperation(value = "当前管理员信息", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<SysAdminDTO> info() {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        SysAdminDTO sysAdminDTO = new SysAdminDTO();
        BeanUtils.copyProperties(userSession, sysAdminDTO);
        sysAdminDTO.setRole(userSession.getRole().getCode());
        sysAdminDTO.setStatus(SysAdminStatus.NORMAL.getCode());
        sysAdminDTO.setModules(userSession.getModules());
        return Result.success(sysAdminDTO);
    }

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建用户", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<Boolean> create(@RequestBody SysAdminDTO sysAdminDTO) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminDTO.getMobile()), "手机号不能为空");
        Assert.isTrue(PhoneUtil.isMobile(sysAdminDTO.getMobile()), "无效手机号");
        SysAdminRole sysAdminRole = SysAdminRole.getRoleByCode(sysAdminDTO.getRole());
        Assert.notNull(sysAdminRole, "无效角色");
        Assert.isTrue(userSession.getRole().getWeight() >= sysAdminRole.getWeight(), "无权限创建该角色账号");
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminDTO.getOrganizationCode()), "组织机构不能为空");

        SysAdmin existAdmin = sysAdminService.getByMobile(sysAdminDTO.getMobile());
        Assert.isNull(existAdmin, "该手机已经存在账号，请更换手机号");

        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setCanCharge(sysAdminDTO.getCharge() == null ? 0 : (sysAdminDTO.getCharge() ? 1 : 0));
        sysAdmin.setMobile(sysAdminDTO.getMobile());
        sysAdmin.setOrganizationCode(sysAdminDTO.getOrganizationCode());
        sysAdmin.setRole(sysAdminRole.getCode());
        sysAdmin.setCreateUserId(userSession.getUserId());
        sysAdmin.setUpdateUserId(userSession.getUserId());
        if(!CollectionUtils.isEmpty(sysAdminDTO.getModules())) {
            String modules = sysAdminDTO.getModules().stream().filter(e -> Objects.nonNull(ModuleEnum.getByCode(e))).map(String::valueOf)
                    .collect(Collectors.joining(","));
            sysAdmin.setModules(modules);
        }
        sysAdminService.add(sysAdmin);
        return Result.success(true);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<Boolean> update(@RequestBody SysAdminDTO sysAdminDTO) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminDTO.getMobile()), "手机号不能为空");
        Assert.isTrue(PhoneUtil.isMobile(sysAdminDTO.getMobile()), "无效手机号");
        Assert.isTrue(Objects.nonNull(sysAdminDTO.getUserId()) && sysAdminDTO.getUserId() > 0, "userId is required");
        SysAdminRole sysAdminRole = SysAdminRole.getRoleByCode(sysAdminDTO.getRole());
        Assert.notNull(sysAdminRole, "无效角色");
        Assert.isTrue(userSession.getRole().getWeight() >= sysAdminRole.getWeight(), "无权限创建该角色账号");
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminDTO.getOrganizationCode()), "组织机构不能为空");

        SysAdmin existAdmin = sysAdminService.getByMobile(sysAdminDTO.getMobile());
        Assert.isTrue((existAdmin == null || existAdmin.getId().equals(sysAdminDTO.getUserId())), "该手机已经存在账号，请更换手机号");

        SysAdmin currentAdmin = sysAdminService.getById(sysAdminDTO.getUserId());
        Assert.notNull(currentAdmin, "未知用户");

        currentAdmin.setCanCharge(sysAdminDTO.getCharge() == null ? 0 : (sysAdminDTO.getCharge() ? 1 : 0));
        currentAdmin.setMobile(sysAdminDTO.getMobile());
        currentAdmin.setOrganizationCode(sysAdminDTO.getOrganizationCode());
        currentAdmin.setRole(sysAdminRole.getCode());
        currentAdmin.setUpdateUserId(userSession.getUserId());
        if(!CollectionUtils.isEmpty(sysAdminDTO.getModules())) {
            String modules = sysAdminDTO.getModules().stream().filter(e -> Objects.nonNull(ModuleEnum.getByCode(e))).map(String::valueOf)
                    .collect(Collectors.joining(","));
            currentAdmin.setModules(modules);
        }
        sysAdminService.update(currentAdmin);
        return Result.success(true);
    }

    @GetMapping(value = "/disable")
    @ApiOperation(value = "禁用用户", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<Boolean> disable(@RequestParam Long userId) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        SysAdmin disAdmin = sysAdminService.getById(userId);
        Assert.notNull(disAdmin, "无效用户");

        SysAdminRole disAdminRole = SysAdminRole.getRoleByCode(disAdmin.getRole());
        Assert.isTrue(userSession.getRole().getWeight() >= disAdminRole.getWeight(), "无权限操作该账号");
        sysAdminService.disabled(userId, userSession.getUserId());
        return Result.success(true);
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "用户列表", httpMethod = "POST")
    @PathRole(role = SysAdminRole.ADMIN)
    public Result<SysAdminPageResp> list(@RequestBody Paging paging) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        SysAdminRole role = userSession.getRole();
        SysAdminQuery sysAdminQuery = new SysAdminQuery();
        if (!role.equals(SysAdminRole.SUPPER_ADMIN)) {
            sysAdminQuery.setCreateUserId(userSession.getUserId());
        }
        Page<SysAdmin> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
                .doSelectPage(() -> sysAdminService.query(sysAdminQuery));

        SysAdminPageResp sysAdminPageResp = new SysAdminPageResp();
        sysAdminPageResp.setTotal(pageResult.getTotal());
        List<SysAdmin> result = pageResult.getResult();
        List<SysAdminDTO> sysAdmins = result.stream().map(e -> {
            SysAdminDTO sysAdminDTO = new SysAdminDTO();
            BeanUtils.copyProperties(e, sysAdminDTO);
            sysAdminDTO.setCharge(e.getCanCharge() == 1);
            sysAdminDTO.setUserId(e.getId());
            String modules = e.getModules();
            if (StringUtils.isNotEmpty(modules)) {
                List<Integer> moduleList = Arrays.stream(modules.split(",")).filter(StringUtils::isNotEmpty).map(Integer::parseInt).collect(Collectors.toList());
                sysAdminDTO.setModules(moduleList);
            }
            return sysAdminDTO;
        }).collect(Collectors.toList());
        sysAdminPageResp.setList(sysAdmins);
        return Result.success(sysAdminPageResp);
    }

}
