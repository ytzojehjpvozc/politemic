package com.xbh.politemic.config;

import com.github.benmanes.caffeine.cache.*;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.srv.PostSrv;
import com.xbh.politemic.common.constant.CommonConstants;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Caffeine: 咖啡因配置
 * @author: ZBoHang
 * @time: 2021/10/15 10:36
 */
@Configuration
public class CaffeineConfig {

    private static final Logger log = LoggerFactory.getLogger(CaffeineConfig.class);

    @Autowired
    private PostSrv postSrv;

    @Bean("postsListCaffeine")
    public LoadingCache<String, List<DiscussPosts>> loadingCache() {

        return Caffeine.newBuilder()
                // 初始容量
                .initialCapacity(CommonConstants.CAFFEINE_CONFIG_INIT_CAPACITY)
                // 最大容量
                .maximumSize(CommonConstants.CAFFEINE_CONFIG_MAX_SIZE)
                // 设置value的软引用 内存不足时gc会回收
                .softValues()
                // 刷新间隔 10 分钟
                .refreshAfterWrite(CommonConstants.CAFFEINE_CONFIG_REFRESH_SPACE_PAGE_GET_POSTS, TimeUnit.SECONDS)
                // 过期时间
                .expireAfterWrite(CommonConstants.CAFFEINE_CONFIG_EXPIRE, TimeUnit.SECONDS)
                // 设置监听
                .writer(new CacheWriter<String, List<DiscussPosts>>() {

                    @Override
                    public void write(@NonNull String key, @NonNull List<DiscussPosts> value) {
                        // 缓存写入或更新 监听
                        log.info("@@@@@已写入Caffeine缓存 key --> " + key);
                    }

                    @Override
                    public void delete(@NonNull String key, @Nullable List<DiscussPosts> value, @NonNull RemovalCause cause) {
                        // 缓存删除 监听
                        log.info("@@@@@已 释放 Caffeine缓存 key --> " + key);
                    }
                })
                .recordStats()
                // 未命中的加载机制
                .build(this.cacheLoader());
    }

    /**
     * 未命中的加载机制
     * @author: ZBoHang
     * @time: 2021/12/14 17:41
     */
    private CacheLoader<String, List<DiscussPosts>> cacheLoader() {
        return new CacheLoader<String, List<DiscussPosts>>() {
            @Override
            public @Nullable List<DiscussPosts> load(@NonNull String key) throws Exception {
                // 未命中咖啡因 可以设置从redis中读取
                log.info("@@@@@未命中 Caffeine 即将从库中查找");
                // redis中未能读取到 返回空值 由业务层从数据库中查找
                return Collections.emptyList();
            }

            /**
             * 刷新时调用的方法
             * @param key key
             * @param oldValue value
             * @return: java.util.List<com.xbh.politemic.biz.post.domain.DiscussPosts>
             * @author: ZBoHang
             * @time: 2021/12/15 16:17
             */
            @Override
            public @Nullable List<DiscussPosts> reload(@NonNull String key, @NonNull List<DiscussPosts> oldValue) throws Exception {

                log.info("@@@@@ Caffeine缓存 reload ······");

                return CacheLoader.super.reload(key, oldValue);
            }
        };
    }
}
