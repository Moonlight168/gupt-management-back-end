package edu.gupt.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import edu.gupt.domain.po.SearchResult;
import edu.gupt.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 服务实现类
 * 该类实现了 ElasticsearchService 接口，用于处理与 Elasticsearch 相关的搜索操作
 */
@Slf4j
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    /**
     * Elasticsearch 客户端
     * 用于与 Elasticsearch 进行通信，执行搜索等操作
     */
    @Autowired
    private ElasticsearchClient esClient;

    /**
     * 执行搜索操作
     * 根据给定的查询字符串在预定义的索引中搜索相关内容，并返回搜索结果列表
     *
     * @param query 查询字符串，用户输入的搜索关键词
     * @return 搜索结果列表，包含匹配的文档信息
     */
    @Override
    public List<SearchResult> search(String query) {
        try {
            List<SearchResult> results = new ArrayList<>();
            // 定义需要搜索的索引类型数组
            String[] types = {"news", "notice"};

            // 遍历每个索引类型，执行搜索操作
            for (String indexName : types) {
                SearchResponse<Object> response = esClient.search(s -> s
                                .index(indexName) // 明确指定索引
                                .query(q -> q
                                        .multiMatch(
                                                m -> m
                                                        .fields("content") // 只匹配 content 字段
                                                        .query(query) // 查询关键字
                                        )
                                )
                                .size(10), // 返回前 10 条记录
                        Object.class
                );
                results.addAll(parseResponse(response));
            }

            return results;
        } catch (IOException e) {
            // 当搜索操作发生 IOException 时，记录错误日志
            log.error("在 elastic 中没有找到对应内容：", e);
        }
        // 如果发生异常或没有结果，返回空列表
        return List.of();
    }

    /**
     * 解析搜索响应
     * 将 Elasticsearch 返回的搜索响应解析为 SearchResult 对象列表
     *
     * @param response 搜索响应对象，包含搜索结果
     * @return 解析后的搜索结果列表
     */
    private List<SearchResult> parseResponse(SearchResponse<?> response) {
        List<SearchResult> resultList = new ArrayList<>();
        // 遍历每个搜索结果，解析并添加到结果列表中
        for (Hit<?> hit : response.hits().hits()) {
            Object source = hit.source();
            // 检查源数据是否有效
            if (isSourceValid(source)) {
                SearchResult searchResult = new SearchResult(
                        String.valueOf(((Map<String, Object>) source).get("type_id")),
                        hit.id(),
                        String.valueOf(((Map<String, Object>) source).get("title")),
                        String.valueOf(((Map<String, Object>) source).get("content"))
                );
                resultList.add(searchResult);
            }
        }
        return resultList;
    }

    /**
     * 检查源数据是否有效
     * 判断给定的源数据对象是否符合预期的类型，以决定是否继续解析
     *
     * @param source 源数据对象
     * @return 如果源数据对象是 Map 类型，则返回 true，否则返回 false
     */
    private boolean isSourceValid(Object source) {
        // 检查源数据是否为 Map 类型
        return source instanceof Map;
    }
}
