package com.xbh.politemic.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 操作redis的工具类
 * @Author: zhengbohang
 * @Date: 2021/10/3 19:29
 */
@Component
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: 检查键是否存在
     * @author: zhengbohang
     * @date: 2021/10/3 19:38
     */
    public Boolean hasKey(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
    }

    /**
     * @description: 设置带有生存时间的 单位: 秒
     * @author: zhengbohang
     * @date: 2021/10/3 19:42
     */
    public void set(String key, String value, Long seconds) {
        this.redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * @description: 拿到key 对应的值
     * @author: zhengbohang
     * @date: 2021/10/3 19:44
     */
    public String get(String key) {
        return String.valueOf(this.redisTemplate.opsForValue().get(key));
    }

    /**
     * 删除匹配key 的键值对
     * @author: ZBoHang
     * @time: 2021/10/11 17:12
     */
    public void del(String key) {
        this.redisTemplate.delete(key);
    }
}
