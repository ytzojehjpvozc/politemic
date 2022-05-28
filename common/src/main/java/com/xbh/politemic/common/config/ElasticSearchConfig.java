package com.xbh.politemic.common.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ElasticSearchConfig: ES配置
 * @author: ZBoHang
 * @time: 2021/10/21 17:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

    @Value("${elasticsearch.scheme}")
    private String scheme;

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

        return new RestHighLevelClient(RestClient.builder(new HttpHost(this.host, this.port, this.scheme)));
    }

}
