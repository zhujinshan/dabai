package com.dabai.proxy.dao;

import com.dabai.proxy.BaseTest;
import com.dabai.proxy.po.UserInfo;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 23:03
 */
public class UserInfoMapperTest extends BaseTest {

    @Resource
    UserInfoMapper userInfoMapper;

    @Test
    public void testSelect() {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("id", 11);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        System.out.println(userInfos);
    }
}
