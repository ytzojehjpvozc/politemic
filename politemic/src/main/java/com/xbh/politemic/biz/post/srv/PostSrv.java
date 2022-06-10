package com.xbh.politemic.biz.post.srv;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.xbh.politemic.bean.ESClient;
import com.xbh.politemic.biz.post.builder.PostBuilder;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.vo.*;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.enums.post.PostConfessionEnum;
import com.xbh.politemic.common.enums.post.PostStatusEnum;
import com.xbh.politemic.common.util.PageUtil;
import com.xbh.politemic.common.util.ServiceAssert;
import com.xbh.politemic.task.AsyncTask;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @PostSrvImpl: 帖子业务层接口实现类
 * @author: ZBoHang
 * @time: 2021/10/15 14:25
 */
@Service
public class PostSrv extends BasePostSrv {

    private static final Logger log = LoggerFactory.getLogger(PostSrv.class);

    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private UserSrv userSrv;
    @Autowired
    private CommentSrv commentSrv;
    @Resource
    private LoadingCache<String, List<DiscussPosts>> postsListCaffeine;
    @Autowired
    private ESClient esClient;

    /**
     * 发布帖子
     * @author: ZBoHang
     * @time: 2021/10/19 15:56
     */
    @Transactional(rollbackFor = Exception.class)
    public String publishPost(PulishPostRequestVO vo, String userId) {
        // 构建一个初始化帖子
        DiscussPosts discussPosts = PostBuilder.buildInitiallyPost(vo, userId);
        // 持久化帖子
        this.insertSelective(discussPosts);
        // 异步审核帖子
        this.asyncTask.auditPost(discussPosts);

        return "帖子发布成功,等待审核!";
    }

    /**
     * 分页获取帖子列表
     * @param vo
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/15 11:37
     */
    public PageUtil<PageGetPostsResponseVO> pageGetPosts(PageGetPostsRequestVO vo, String token) {

        Integer pageNum = vo.getCurrentPageNum();

        Integer pageSize = vo.getCurrentPageSize();

        SysUser sysUser = null;
        // 私密帖子要检查令牌
        if (StrUtil.equals(PostConfessionEnum.PRIVACY.getCode(), vo.getConfessed())) {

            sysUser = this.userSrv.getUserInfoByToken(token);

            ServiceAssert.notNull(sysUser, "权限异常!");
        }
        // 构建分页获取帖子列表的帖子实体
        DiscussPosts dp = PostBuilder.buildPageGetPosts(vo, sysUser);
        // 查找个数
        Integer count = this.selectCount(dp);
        // 尝试从 caffeine 中获取
        List<DiscussPosts> list = this.postsListCaffeine.get(CommonConstants.CAFFEINE_CONFIG_PAGE_GET_POSTS_PRE + dp.getType() + StrUtil.UNDERLINE + dp.getStatus()
                // key中拼接分页参数
                + StrUtil.UNDERLINE + dp.getConfessed() + StrUtil.UNDERLINE + pageNum + StrUtil.UNDERLINE + pageSize);

        if (list == null || list.isEmpty()) {
            // 获取帖子列表 从数据库中
            list = this.getPostsOnDB(dp, pageNum, pageSize);
        }
        // 构建分页获取帖子列表响应对象
        List<PageGetPostsResponseVO> voList = list.stream().map(PageGetPostsResponseVO::build)
                // 排序收集
                .sorted((o1, o2) -> o1.getCreateTime().after(o2.getCreateTime()) ? -1 : 1).collect(Collectors.toList());

        return new PageUtil<>(pageNum, pageSize, count.longValue(), voList);
    }

    /**
     * 查询帖子详情
     * @param postId 帖子id
     * @param token 用户令牌
     * @return: com.xbh.politemic.biz.post.vo.GetPostDetailResponseVO
     * @author: ZBoHang
     * @time: 2021/12/15 17:39
     */
    public Map<String, Object> getPostDetail(String postId, String token) {
        // 查询帖子
        DiscussPosts dp = this.selectByPrimaryKey(postId);

        ServiceAssert.notNull(dp, "查询的帖子不存在!");
        // 未审核 或者 被删除
        ServiceAssert.isTrue(StrUtil.equals(PostStatusEnum.NORMAL.getCode(), dp.getStatus())

                || StrUtil.equals(PostStatusEnum.ESSENCE.getCode(), dp.getStatus()), "查询的帖子未审核或者已删除!");
        // 帖子如果是私密的
        if (StrUtil.equals(PostConfessionEnum.PRIVACY.getCode(), dp.getConfessed())) {

            String userId = this.userSrv.getUserInfo(token).getId();

            ServiceAssert.isTrue(userId.equals(dp.getUserId()), "没有权限查看该帖子!");
        }

        GetPostDetailResponseVO vo = GetPostDetailResponseVO.build(dp);

        List<Map<String, Object>> commentList = this.commentSrv.getCommentsByPostId(vo.getId());

        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("body", vo);
                put("comment", commentList);
            }
        });
    }

    /**
     * 获取帖子列表 从数据库中
     * @param dp 实体条件
     * @param pageNum 当前页
     * @param pageSize 当前页数据量
     * @return: java.util.List<com.xbh.politemic.biz.post.vo.PageGetPostsResponseVO>
     * @author: ZBoHang
     * @time: 2021/12/15 16:02
     */
    private List<DiscussPosts> getPostsOnDB(DiscussPosts dp, Integer pageNum, Integer pageSize) {
        // 查找到数据
        List<DiscussPosts> list = this.selectByRowBounds(dp, pageNum, pageSize);
        // 存储到caffeine中
        this.postsListCaffeine.put(CommonConstants.CAFFEINE_CONFIG_PAGE_GET_POSTS_PRE + dp.getType() + StrUtil.UNDERLINE + dp.getStatus()
                // key中拼接分页参数
                + StrUtil.UNDERLINE + dp.getConfessed() + StrUtil.UNDERLINE + pageNum + StrUtil.UNDERLINE + pageSize, list);

       return list;
    }

    /**
     * 通过关键词查询帖子
     * @param vo vo
     * @return: void
     * @author: ZBoHang
     * @time: 2022/1/5 10:11
     */
    public PageUtil<PageGetPostsResponseVO> searchPosts(PageSearchPostsRequestVO vo) {
        // 当前页
        Integer currentPageNum = vo.getCurrentPageNum();
        // 每页
        Integer currentPageSize = vo.getCurrentPageSize();

        String key = vo.getKey();
        // 从es中查
        SearchHits searchHits = this.esClient.splitWordSearch(CommonConstants.ES_POST_INDEX_NAME, key, (currentPageNum - 1) * currentPageSize, currentPageSize);
        // 总数
        long total = searchHits.getTotalHits().value;

        SearchHit[] hits = searchHits.getHits();

        List<PageGetPostsResponseVO> voList = Arrays.stream(hits)
                // 获取帖子id
                .map(SearchHit::getId)
                // 查询帖子详情
                .map(this::selectByPrimaryKey)

                .map(PageGetPostsResponseVO::build).collect(Collectors.toList());

        Set<String> ids = voList.stream()
                // 过滤掉公开的
                .filter(v -> StrUtil.equals(PostConfessionEnum.PRIVACY.getCode(), v.getConfessed()))

                .map(PageGetPostsResponseVO::getId)

                .collect(Collectors.toSet());

        if (!ids.isEmpty()) {

            for (String id : ids) {
                // 循环删除 私密帖
                this.esClient.deleteDocument(CommonConstants.ES_POST_INDEX_NAME, id);
            }
        }

        List<PageGetPostsResponseVO> resultList = voList.stream().filter(v -> !ids.contains(v.getId())).collect(Collectors.toList());

        return new PageUtil<>(currentPageNum, currentPageSize, ((long) resultList.size()), resultList);
    }

    /**
     * 通过帖子id 校验帖子是否公开 公开-ture 私密-false
     * @param postId 帖子id
     * @return: boolean
     * @author: ZBoHang
     * @time: 2022/1/5 11:14
     */
    public boolean checkPostConfessed(String postId) {

        DiscussPosts discussPosts = this.selectByPrimaryKey(postId);

        return StrUtil.equals(PostConfessionEnum.CONFESSED.getCode(), discussPosts.getConfessed()) ? Boolean.TRUE : Boolean.FALSE;
    }
}
