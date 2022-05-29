package com.xbh.politemic.common.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ElasticSearchConfig: ES配置
 * @author: ZBoHang
 * @time: 2021/10/21 17:29
 */
@Data
@Configuration
public class ElasticSearchConfig {

    private static String schema = "http"; // 使用的协议

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

    /**
     * 构建 rest 客户端
     * @return: org.elasticsearch.client.RestHighLevelClient
     * @author: ZBoHang
     * @time: 2021/12/23 16:27
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {

        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, schema)));
    }

}
