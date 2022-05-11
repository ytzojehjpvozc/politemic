package com.xbh.politemic.biz.post.srv;

import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.mapper.CommentMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseCommentSrv: base 评论 srv
 * @author: ZBoHang
 * @time: 2021/12/15 15:48
 */
@Service
public class BaseCommentSrv extends CommonSrv<Comment> {

    @Resource
    protected CommentMapper commentMapper;
}
