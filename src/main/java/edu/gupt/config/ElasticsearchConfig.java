package edu.gupt.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String ELASTICSEARCH_URL;

    @Value("${spring.elasticsearch.rest.port}")
    private int ELASTICSEARCH_PORT;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // 1. 创建低级客户端
        RestClient restClient = RestClient.builder(
                new HttpHost(ELASTICSEARCH_URL, ELASTICSEARCH_PORT)
        ).build();

        // 2. 创建Transport层
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper() // 使用Jackson映射器
        );

        // 3. 创建正式客户端
        return new ElasticsearchClient(transport);
    }

    @Bean
    public JsonpMapper jsonpMapper() {
        return new JacksonJsonpMapper();
    }
}