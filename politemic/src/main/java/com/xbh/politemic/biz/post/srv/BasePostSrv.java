package com.xbh.politemic.biz.post.srv;

import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.mapper.DiscussPostsMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BasePostSrv: base 帖子 srv
 * @author: ZBoHang
 * @time: 2021/12/9 16:33
 */
@Service
public class BasePostSrv extends CommonSrv<DiscussPosts> {

    @Resource
    protected DiscussPostsMapper discussPostsMapper;
}
