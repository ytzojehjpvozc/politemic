package com.xbh.politemic.bean;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @ESClient: es 客户端
 * @author: ZBoHang
 * @time: 2021/12/23 16:31
 */
@Slf4j
@Component
public class ESClient {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @param index 索引名称
     * @return: boolean
     * @author: ZBoHang
     * @time: 2021/12/23 16:51
     */
    public void createIndex(String index) {

        try {
            if (!isExistIndex(index)) {

                CreateIndexResponse response = this.restHighLevelClient.indices().create(new CreateIndexRequest(index), RequestOptions.DEFAULT);

                log.info("index: " + index + " create result -->>> " + response.isAcknowledged());

            }
        } catch (IOException e) {

            log.error("create es index error!");
        }
    }

    /**
     * 判断索引是否存在 true-存在 false-不存在
     * @param index 索引
     * @return: boolean
     * @author: ZBoHang
     * @time: 2021/12/23 17:04
     */
    public boolean isExistIndex(String index) {

        try {
            boolean flag = this.restHighLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);

            return flag;

        } catch (IOException e) {

            log.error("find es index isExist error!");
        }

        return Boolean.TRUE;
    }

    /**
     * 删除指定索引
     * @param index 索引
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/23 17:22
     */
    public void deleteIndex(String index) {

        try {
            AcknowledgedResponse response = this.restHighLevelClient.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);

            log.info("index: " + index + " delete result -->>> " + response.isAcknowledged());

        } catch (IOException e) {

            log.error("delete index error!");
        }
    }

    /**
     * 创建 文档
     * @param entity 实体数据
     * @param index 索引
     * @param id id
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/23 17:39
     */
    public void createDocument(Object entity, String index, String id) {
        // 索引不存在时创建
        if (!this.isExistIndex(index)) {

            this.createIndex(index);
        }

        IndexRequest request = new IndexRequest(index)

                .id(id)

                .timeout(TimeValue.timeValueSeconds(3000))

                .source(JSONUtil.toJsonStr(entity), XContentType.JSON);

        try {
            IndexResponse response = this.restHighLevelClient.index(request, RequestOptions.DEFAULT);

            log.info("index: " + index + " create document -->>> " + response.status());

        } catch (IOException e) {

            log.error("create document error!");
        }
    }

    /**
     * 获取文档信息
     * @param index 索引
     * @param id id
     * @return: org.elasticsearch.action.get.GetResponse
     * @author: ZBoHang
     * @time: 2021/12/24 8:58
     */
    public GetResponse getDocument(String index, String id) {

        try {
            GetResponse response = this.restHighLevelClient.get(new GetRequest(index, id), RequestOptions.DEFAULT);

            log.info("response source map -->>> " + response.getSourceAsMap());

            log.info("response source str -->>> " + response.getSourceAsString());

            return response;

        } catch (IOException e) {

            log.error("get es document error!");
        }

        return null;
    }

    /**
     * 修改文档内容
     * @param entity 要修改的实体
     * @param index 索引
     * @param id id
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/24 9:21
     */
    public void updateDocument(Object entity, String index, String id) {

        try {
            UpdateResponse response = this.restHighLevelClient.update(new UpdateRequest(index, id).doc(entity).timeout(TimeValue.MINUS_ONE), RequestOptions.DEFAULT);

            log.info("update es document status -->>> " + response.status());

        } catch (IOException e) {

            log.error("update document error!");
        }
    }

    /**
     * 删除文档信息
     * @param index 索引
     * @param id id
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/24 9:28
     */
    public void deleteDocument(String index, String id) {

        try {

            DeleteResponse response = this.restHighLevelClient.delete(new DeleteRequest(index, id).timeout(TimeValue.MINUS_ONE), RequestOptions.DEFAULT);

            log.info("delete es document status -->>> " + response.status());

        } catch (IOException e) {

            log.error("delete es document error!");
        }
    }

    /**
     * 批量插入文档数据 返回 false 代表成功
     * @param map id和实体数据 k-v形式对应
     * @param index 索引
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/24 9:41
     */
    public boolean bulkDocument(Map<String, Object> map, String index) {

        BulkRequest bulkRequest = new BulkRequest().timeout(TimeValue.MINUS_ONE);

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            String id = entry.getKey();

            Object entity = entry.getValue();

            bulkRequest.add(new IndexRequest(index)

                    .id(id)

                    .source(entity));
        }

        try {
            BulkResponse responses = this.restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

            log.info("es bulk document result -->>> " + responses.hasFailures());

            return responses.hasFailures();

        } catch (IOException e) {

            log.error("es bulk document error!");
        }

        return Boolean.TRUE;
    }

    /**
     * 精确查询
     *      termQuery
     * @param index 索引
     * @param name 字段名
     * @param value 字段值
     * @return: org.elasticsearch.search.SearchHit[]
     * @author: ZBoHang
     * @time: 2021/12/24 11:11
     */
    public SearchHit[] accurateSearch(String index, String name, Object value) {
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().timeout(TimeValue.MINUS_ONE);
        // 构建高亮
        searchSourceBuilder.highlighter(new HighlightBuilder());
        // 精确查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
        // 设置条件
        searchSourceBuilder.query(termQueryBuilder);
        // 设置请求
        SearchRequest request = new SearchRequest(index).source(searchSourceBuilder);
        // 分页 默认 1-10
        // searchSourceBuilder.from(0).size(10);

        try {
            // 发送请求
            SearchResponse response = this.restHighLevelClient.search(request, RequestOptions.DEFAULT);

            SearchHit[] hits = response.getHits().getHits();

            Arrays.stream(hits).forEach(documentFields -> log.info("es termQuery result -->>> " + documentFields.getSourceAsString()));

            return hits;

        } catch (IOException e) {

            log.error("es termQuery error!");
        }

        return null;
    }

}
