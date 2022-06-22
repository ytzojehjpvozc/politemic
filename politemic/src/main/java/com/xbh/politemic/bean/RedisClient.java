package com.xbh.politemic.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
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

    /**
     * 添加值 到指定集合set
     * @author: ZBoHang
     * @time: 2022/1/6 10:22
     */
    public void sadd(String key, String value) {
        this.redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除值 从指定集合set
     * @author: ZBoHang
     * @time: 2022/1/6 11:01
     */
    public void sremove(String key, String value) {
        this.redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 查找指定 键值 是否存在
     * @author: ZBoHang
     * @time: 2022/1/6 11:22
     */
    public boolean isMember(String key, String value) {
       return this.redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取指定set的size
     * @author: ZBoHang
     * @time: 2022/1/6 11:56
     */
    public Long ssize(String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    /**
     * 新增 统计(类型)
     * @param key 键
     * @param value 值
     * @return: void
     * @author: ZBoHang
     * @time: 2022/1/17 13:10
     */
    public void addHyperLog(String key, String value) {
        this.redisTemplate.opsForHyperLogLog().add(key, value);
    }

    /**
     * 计数
     * @param key 键
     * @return: java.lang.Long
     * @author: ZBoHang
     * @time: 2022/1/17 13:25
     */
    public Long sizeHyperLog(String key) {
        return this.redisTemplate.opsForHyperLogLog().size(key);
    }

    /**
     * 合并所有旧键结果至新键
     * @param newKey 新键
     * @param oldKeyList 旧键集合
     * @return: java.lang.Long
     * @author: ZBoHang
     * @time: 2022/1/17 13:25
     */
    public Long unionHyperLog(String newKey, List<String> oldKeyList) {
        return this.redisTemplate.opsForHyperLogLog().union(newKey, oldKeyList.toArray());
    }

    /**
     * 移除指定键
     * @param key 键
     * @return: void
     * @author: ZBoHang
     * @time: 2022/1/17 13:27
     */
    public void deleteHyperLog(String key) {
        this.redisTemplate.opsForHyperLogLog().delete(key);
    }
}
