package com.xbh.politemic.biz.user.mapper;

import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.common.imapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends CommonMapper<SysUser> {
    int selectByEmailOrUserName(@Param("userName") String userName, @Param("email") String email);

}