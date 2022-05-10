package com.xbh.politemic.config;

import com.github.benmanes.caffeine.cache.*;
import com.xbh.politemic.biz.post.srv.BasePostSrv;
import com.xbh.politemic.common.constant.CommonConstants;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
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
    private BasePostSrv basePostSrv;

    @Bean
    public LoadingCache<String, Map<String, Object>> loadingCache() {

        return Caffeine.newBuilder()
                // 初始容量
                .initialCapacity(CommonConstants.CAFFEINE_CONFIG_INIT_CAPACITY)
                // 最大容量
                .maximumSize(CommonConstants.CAFFEINE_CONFIG_MAX_SIZE)
                // 设置value的软引用 内存不足时gc会回收
                .softValues()
                // 刷新间隔 10 分钟
                .refreshAfterWrite(CommonConstants.CAFFEINE_CONFIG_REFRESH_SPACE, TimeUnit.SECONDS)
                // 过期时间
                .expireAfterWrite(CommonConstants.CAFFEINE_CONFIG_EXPIRE, TimeUnit.SECONDS)
                // 设置监听
                .writer(new CacheWriter<String, Map<String, Object>>() {

                    @Override
                    public void write(@NonNull String key, @NonNull Map<String, Object> value) {
                        // 缓存写入或更新 监听
                        log.info("@@@@@已写入Caffeine缓存 key --> " + key);
                    }

                    @Override
                    public void delete(@NonNull String key, @Nullable Map<String, Object> value, @NonNull RemovalCause cause) {
                        // 缓存删除 监听
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
    private CacheLoader<String, Map<String, Object>> cacheLoader() {
        return new CacheLoader<String, Map<String, Object>>() {
            @Override
            public @Nullable Map<String, Object> load(@NonNull String key) throws Exception {

                // 未命中咖啡因 从redis中读取

                // redis中未能读取到 从数据库中查找
                return new HashMap<String, Object>(){
                    {
                        put("1", "222");
                    }
                };
            }
        };
    }
}
