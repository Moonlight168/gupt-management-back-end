package edu.gupt.domain.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult {
    String typeId;
    String id;
    String title;
    String content;
}
