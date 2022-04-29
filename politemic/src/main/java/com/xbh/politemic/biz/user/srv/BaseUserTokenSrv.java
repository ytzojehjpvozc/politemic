package com.xbh.politemic.biz.user.srv;

import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.biz.user.mapper.UserTokenMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseUserTokenSrv: base 用户token srv
 * @author: ZBoHang
 * @time: 2021/12/9 16:07
 */
@Service
public class BaseUserTokenSrv extends CommonSrv<UserToken> {

    @Resource
    protected UserTokenMapper userTokenMapper;

}
