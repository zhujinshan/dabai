package com.dabai.proxy.facade;

import com.dabai.proxy.dao.UserInfoCustomMapper;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.po.UserInfoQueryResult;
import com.dabai.proxy.query.MemberInfoQuery;
import com.dabai.proxy.req.MemberInfoQueryReq;
import com.dabai.proxy.req.Paging;
import com.dabai.proxy.resp.MemberInfoQueryResp;
import com.dabai.proxy.resp.UserInfoQueryDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 18:09
 */
@Component
public class MemberInfoFacade {

    @Resource
    private UserInfoCustomMapper userInfoCustomMapper;

    public MemberInfoQueryResp query(MemberInfoQueryReq memberInfoQueryReq) {
        MemberInfoQuery memberInfoQuery = new MemberInfoQuery();
        BeanUtils.copyProperties(memberInfoQueryReq, memberInfoQuery);

        Paging paging = memberInfoQueryReq.getPaging();
        if (paging == null) {
            paging = new Paging();
        }

        Page<UserInfoQueryResult> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
                .doSelectPage(() -> userInfoCustomMapper.queryUserInfo(memberInfoQuery));

        MemberInfoQueryResp resp = new MemberInfoQueryResp();
        resp.setTotal(pageResult.getTotal());
        List<UserInfoQueryResult> result = pageResult.getResult();
        List<UserInfoQueryDTO> userInfoQuerys = result.stream().map(e -> {
            UserInfoQueryDTO userInfoQueryDTO = new UserInfoQueryDTO();
            BeanUtils.copyProperties(e, userInfoQueryDTO);
            UserSignStatusEnum signStatusEnum = UserSignStatusEnum.getByCode(e.getSignStatus());
            if (Objects.equals(signStatusEnum, UserSignStatusEnum.SUCCESS)) {
                userInfoQueryDTO.setStatus(1);
                userInfoQueryDTO.setRealNameTime(e.getSignUtime());
            } else {
                userInfoQueryDTO.setStatus(0);
            }
            userInfoQueryDTO.setChangeAgent(false);
            if (Objects.nonNull(e.getOriginalIdentityTag()) && e.getOriginalIdentityTag() == 0
                    && Objects.nonNull(e.getCurrentIdentityTag()) && e.getCurrentIdentityTag() == 1) {
                userInfoQueryDTO.setChangeAgent(true);
                userInfoQueryDTO.setChangeAgentTime(e.getIdentityChangeTime());
            }
            userInfoQueryDTO.setRegisterIdentityTag(e.getRegisterIdentityTag());
            return userInfoQueryDTO;
        }).collect(Collectors.toList());
        resp.setUserList(userInfoQuerys);
        return resp;
    }
}
