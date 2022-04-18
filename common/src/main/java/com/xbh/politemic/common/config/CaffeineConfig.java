package com.xbh.politemic.common.config;

import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Caffeine: 咖啡因配置
 * @author: ZBoHang
 * @time: 2021/10/15 10:36
 */
@Configuration
public class CaffeineConfig {

    private static final Logger log = LoggerFactory.getLogger(CaffeineConfig.class);
    /**
     * 初始容量
     */
    private final int INIT_CAPACITY = 36;
    /**
     * 初始容量
     */
    private final long MAX_SIZE = 128;
    /**
     * 初始容量
     */
    private final long REFRESH_SPACE_SECOND = 180;

    @Bean
    public LoadingCache<String, String> loadingCache() {

        return Caffeine.newBuilder()
                .initialCapacity(this.INIT_CAPACITY)
                .maximumSize(this.MAX_SIZE)
                // 设置value的软引用 内存不足时gc会回收
                .softValues()
                // .refreshAfterWrite(this.REFRESH_SPACE_SECOND, TimeUnit.SECONDS)
                .expireAfterWrite(600, TimeUnit.SECONDS)
                // 设置监听
                .writer(new CacheWriter<String, String>() {

                    @Override
                    public void write(@NonNull String key, @NonNull String value) {
                        // 缓存写入或更新 监听
                        log.info("@@@@@已写入Caffeine缓存 key --> " + key);
                    }

                    @Override
                    public void delete(@NonNull String key, @Nullable String value, @NonNull RemovalCause cause) {
                        // 缓存删除 监听
                    }
                })
                // .recordStats()
                // 未命中的加载机制
                .build(this.cacheLoader());
    }

    private CacheLoader<String, String> cacheLoader() {
        return new CacheLoader<String, String>() {
            @Override
            public @Nullable String load(@NonNull String key) throws Exception {

                return "abcdefg";
            }
        };
    }
}
