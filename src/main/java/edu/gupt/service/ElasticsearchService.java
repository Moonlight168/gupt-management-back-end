package edu.gupt.service;

import edu.gupt.domain.po.SearchResult;

import java.util.List;

public interface ElasticsearchService {
    List<SearchResult> search(String query);
}