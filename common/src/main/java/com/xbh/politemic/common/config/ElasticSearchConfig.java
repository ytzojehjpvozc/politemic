package com.xbh.politemic.common.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @ElasticSearchConfig: ES配置
 * @author: ZBoHang
 * @time: 2021/10/21 17:29
 */
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return null;
    }
}
