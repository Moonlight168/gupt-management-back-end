package edu.gupt.utils;

import edu.gupt.domain.dto.PageDTO;

public class ValidateUtil {
    public static boolean isPageValid(PageDTO pageDTO) {
        return pageDTO.getTypeId() == null || pageDTO.getCurrentPage() <= 0 || pageDTO.getPageSize() <= 0;
    }
}
