package com.xbh.politemic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @AsyncThreadPoolConfig: 异步任务线程池配置
 * @author: ZBoHang
 * @time: 2021/10/9 16:12
 */
@EnableAsync
@Configuration
public class AsyncThreadPoolConfig {

    /**
     * 自定义异步线程池
     */
    @Bean(name = "CustomAsyncThreadPoolExecutor")
    public Executor getExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        threadPoolTaskExecutor.setCorePoolSize(2);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(8);
        // 空闲时间秒数
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //缓冲队列数
        threadPoolTaskExecutor.setQueueCapacity(25);
        // 线程池名前缀
        threadPoolTaskExecutor.setThreadNamePrefix("Async-*-Thread-*-Pool");
        // 拒绝任务的处理策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPoolTaskExecutor;
    }
}
