package com.xbh.politemic.biz.post.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.mapper.DiscussPostsMapper;
import com.xbh.politemic.task.AsyncTask;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.StrKit;
import com.xbh.politemic.common.util.ThreadLocalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @PostSrvImpl: 帖子业务层接口实现类
 * @author: ZBoHang
 * @time: 2021/10/15 14:25
 */
@Service
public class PostSrv {

    private static final Logger log = LoggerFactory.getLogger(PostSrv.class);

    /**
     * 创建帖子初始类型 1-普通帖子
     */
    private final String CREATE_POST_INIT_TYPE = "1";

    @Resource
    private DiscussPostsMapper discussPostsMapper;
    @Autowired
    private AsyncTask asyncTask;

    /**
     * 发布帖子
     * @author: ZBoHang
     * @time: 2021/10/19 15:56
     */
    public Result publishPost(DiscussPosts discussPosts) {
        if (StrUtil.isBlank(discussPosts.getTitle())) {
            return Result.failure("帖子主题不能为空白");
        }
        if (StrUtil.isBlank(discussPosts.getContent())) {
            return Result.failure("帖子内容不能为空白");
        }
        String postId = StrKit.getUUID();
        // 初始化帖子信息
        discussPosts.setId(postId)
                          .setUserId(ThreadLocalUtils.getUserId())
                          .setCreateTime(new Timestamp(System.currentTimeMillis()))
                          .setStatus(Constants.CREATE_POST_INIT_STATUS)
                          .setType(this.CREATE_POST_INIT_TYPE);
        // 持久化帖子
        this.savePostAndAsyncSend(discussPosts);
        // 异步审核帖子
        this.asyncTask.auditPost(discussPosts);
        return Result.success("帖子发送成功,正在审核中···");
    }

    /**
     * 保存帖子信息并异步发送至消息队列审核
     * @author: ZBoHang
     * @time: 2021/10/19 15:56
     */
    @Transactional(rollbackFor = Exception.class)
    void savePostAndAsyncSend(DiscussPosts discussPosts) {
        // 持久化帖子
        this.discussPostsMapper.insertSelective(discussPosts);
    }

}
