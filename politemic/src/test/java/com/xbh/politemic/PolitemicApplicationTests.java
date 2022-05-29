package com.xbh.politemic;

import com.xbh.politemic.bean.ESClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PolitemicApplicationTests {

    // @Autowired
    // LoadingCache<String, String> loadingCache;
    @Autowired
    private ESClient esClient;

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

        // 测试es
        // 创建索引
        // this.esClient.createIndex("test123");
        // 删除索引
        // this.esClient.deleteIndex("test123");
        // 创建文档
        // this.esClient.createDocument(new ExceptionLog()
        //                 .setId(7)
        //         , "test123");
        //
        // this.esClient.getDocument("test123", "1");

    }

}
