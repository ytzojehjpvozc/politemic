package com.xbh.politemic.biz.post.srv;

import com.xbh.politemic.biz.post.builder.PostBuilder;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.task.AsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private BasePostSrv basePostSrv;
    @Autowired
    private AsyncTask asyncTask;

    /**
     * 发布帖子
     * @author: ZBoHang
     * @time: 2021/10/19 15:56
     */
    public String publishPost(String title, String content, String userId) {
        // 构建一个初始化帖子
        DiscussPosts discussPosts = PostBuilder.buildInitiallyPost(title, content, userId);
        // 持久化帖子
        this.basePostSrv.insertSelective(discussPosts);
        // 异步审核帖子
        this.asyncTask.auditPost(discussPosts);

        return "帖子发布成功,等待审核!";
    }
}
