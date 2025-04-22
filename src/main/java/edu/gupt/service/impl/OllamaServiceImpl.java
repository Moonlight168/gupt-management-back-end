package edu.gupt.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gupt.domain.constants.InfoType;
import edu.gupt.domain.po.SearchResult;
import edu.gupt.service.OllamaService;
import edu.gupt.utils.FaqMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * OllamaService的实现类，用于处理与Ollama模型相关的查询
 */
@Service
public class OllamaServiceImpl implements OllamaService {

    @Autowired
    private FaqMatcher faqMatcher;
    private static final Logger log = LoggerFactory.getLogger(OllamaServiceImpl.class);
    private final String OLLAMA_SERVER_URL = "http://localhost:11434";
    @Autowired
    private final ElasticsearchServiceImpl elasticsearchService;

    public OllamaServiceImpl(ElasticsearchServiceImpl elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    /**
     * 查询Ollama模型的答案
     * 首先尝试使用FAQ匹配，如果失败则查询Elasticsearch并结合结果查询Ollama
     *
     * @param query 查询问题
     * @param mode  模式，如果为true则直接返回搜索结果的类型和ID
     * @return Ollama模型的回答或搜索结果的字符串表示
     */
    @Override
    public String queryOllama(String query, boolean mode) {
        try {
            // 先尝试FAQ（Frequently Asked Questions）匹配
            Optional<String> faqAnswer = faqMatcher.match(query);
            if (faqAnswer.isPresent()) {
                return faqAnswer.get();
            }

            // 判断是否为搜索请求,为真时返回访问链接
            if (mode) {
                List<SearchResult> searchResults = elasticsearchService.search(query);
                if (searchResults.isEmpty()) {
                    log.info("未找到相关内容");
                    return null;
                }
                // 返回搜索到的文章类型和id
                StringBuilder sb = new StringBuilder();
                for (SearchResult result : searchResults) {
                    sb.append(InfoType.POTypeToVOType(Integer.parseInt(result.getTypeId()))).append(":").append(result.getId()).append(":").append(result.getTitle()).append(" ");
                }
                return sb.toString();
            }

            HttpURLConnection connection;
            /*if (!searchResults.isEmpty()) {
                //将Elasticsearch的查询结果作为prompt传入Ollama,如果存在
                String combinedPrompt = "根据以下信息回答问题:\n" +
                        String.join("\n", searchResults.stream()
                                .map(result -> result.getTitle() + ": " + result.getContent()) // 手动拼接字段
                                .toList()) +
                        "\n用户搜索的问题: " + query;
                connection = getConnection(combinedPrompt);
            } else */connection = getConnection(query);

            // 检查HTTP响应码
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                // 读取错误流
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                log.error("请求失败: {} - {}", responseCode, errorResponse);
                return null;
            }

            // 读取正常响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            // 响应处理部分
            StringBuilder fullResponse = new StringBuilder();
            ObjectMapper mapper = new ObjectMapper();

            while ((inputLine = in.readLine()) != null) {
                try {
                    JsonNode node = mapper.readTree(inputLine);
                    if (node.has("response")) {
                        fullResponse.append(node.get("response").asText());
                    }
                    if (node.has("done") && node.get("done").asBoolean()) {
                        break; // 流式响应结束
                    }
                } catch (Exception e) {
                    log.error("Error occurred while processing JSON response: {}", e.getMessage(), e);
                }
            }
            return fullResponse.toString();

        } catch (Exception e) {
            log.error("Error occurred while querying Ollama: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 创建到Ollama服务器的HTTP连接
     *
     * @param query 查询问题
     * @return HttpURLConnection对象
     * @throws IOException 如果连接过程中发生IO异常
     */
    private HttpURLConnection getConnection(String query) throws IOException {
        URL url = new URL(OLLAMA_SERVER_URL + "/api/generate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // 使用 ObjectMapper 自动生成 JSON 请求体
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> requestBody = Map.of(
                "model", "qwen2.5:0.5b",
                "prompt", query
        );
        String jsonInputString = mapper.writeValueAsString(requestBody);

        // 将 JSON 数据写入 HTTP 连接输出流
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
    }
}
