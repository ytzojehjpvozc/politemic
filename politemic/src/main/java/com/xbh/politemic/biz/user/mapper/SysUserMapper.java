package com.xbh.politemic.biz.user.mapper;

import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.common.imapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends CommonMapper<SysUser> {
    /**
     * 通过邮箱或用户名查询用户个数
     * @param userName 用户名
     * @param email 邮箱
     * @return: int
     * @author: ZBoHang
     * @time: 2021/12/13 15:50
     */
    int selectByEmailOrUserName(@Param("userName") String userName, @Param("email") String email);

}