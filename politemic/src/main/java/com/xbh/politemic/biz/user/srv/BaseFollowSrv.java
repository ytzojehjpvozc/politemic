package com.xbh.politemic.biz.user.srv;

import com.xbh.politemic.biz.user.domain.Follow;
import com.xbh.politemic.biz.user.mapper.FollowMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseFollowSrv: base 关注 srv
 * @author: ZBoHang
 * @time: 2022/1/6 15:27
 */
@Service
public class BaseFollowSrv extends CommonSrv<Follow> {

    @Resource
    protected FollowMapper followMapper;
}
