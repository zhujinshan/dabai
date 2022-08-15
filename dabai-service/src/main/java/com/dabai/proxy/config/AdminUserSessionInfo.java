package com.dabai.proxy.config;

import com.dabai.proxy.enums.SysAdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/9 23:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserSessionInfo {

    private Long userId;

    private String mobile;

    private SysAdminRole role;

    private List<String> organizationCodes;

    private Boolean charge;

    private List<Integer> modules;

    private String name;

}
