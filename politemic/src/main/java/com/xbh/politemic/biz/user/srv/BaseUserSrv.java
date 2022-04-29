package com.xbh.politemic.biz.user.srv;

import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.mapper.SysUserMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseUserSrv: base 用户srv
 * @author: ZBoHang
 * @time: 2021/12/9 15:56
 */
@Service
public class BaseUserSrv extends CommonSrv<SysUser> {

    @Resource
    protected SysUserMapper sysUserMapper;

}
