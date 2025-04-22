package edu.gupt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FaqMatcher {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<String> match(String query) {
        // 使用SQL通配符匹配（示例）
        String sql = "SELECT answer FROM faq WHERE ? LIKE CONCAT('%', question_pattern, '%') LIMIT 1";
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, String.class, query)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}