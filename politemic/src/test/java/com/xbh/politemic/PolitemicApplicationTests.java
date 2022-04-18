package com.xbh.politemic;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.xbh.politemic.biz.notice.mapper.NoticeMapper;
import com.xbh.politemic.biz.user.mapper.SysUserMapper;
import com.xbh.politemic.task.AsyncTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@SpringBootTest
class PolitemicApplicationTests {

    @Autowired
    Environment environment;
    @Resource
    SysUserMapper sysUserMapper;
    @Autowired
    LoadingCache<String, String> loadingCache;
    @Autowired
    AsyncTask asyncTask;
    @Resource
    NoticeMapper noticeMapper;

    @Test
    void contextLoads() throws Exception {
        //自定义配置
        // String name = environment.getProperty("name");
        // System.out.println(name);

        // http请求
        // String s = HttpUtils.get("https://v1.jinrishici.com/all.json");
        // System.out.println(s);

        // 尝试解析尾巴
        // Example example = new Example(SysUser.class);
        // Example.Criteria criteria = example.createCriteria();
        // criteria.andEqualTo("id", "58fd6cbeda614c67a14f83a6d0fff4b4");
        // SysUser sysUser = sysUserMapper.selectOneByExample(example);
        // String tail = sysUser.getTail();
        // JSONObject jsonObject = JSONObject.parseObject(tail);
        // System.out.println(jsonObject.getString("content"));
        // loadingCache.put("abc", "cba");
        // String abc = loadingCache.get("abc");
        // String abc1 = loadingCache.get("abc");
        // String abc2 = loadingCache.get("abc");
        // String abc3 = loadingCache.get("aa");
        // loadingCache.refresh("aa");
        // CacheStats stats = loadingCache.stats();
        // long l = stats.hitCount();
        // double v = stats.hitRate();


    }

}
