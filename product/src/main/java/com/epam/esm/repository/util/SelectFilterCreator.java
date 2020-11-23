package com.epam.esm.repository.util;

import com.epam.esm.util.ParamName;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectFilterCreator {

    private static String SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS = "SELECT g FROM GiftCertificate g LEFT JOIN fetch g.tags t ";

    private static final String WHERE = " where ";
    private static final String AND = " and ";
    private static final String END_OF_LIKE = "%' ";
    private static final String LIKE = " like '%";
    private static final String ORDER_BY = " order by ";
    private static final String SPACE = " ";
    private static final List<String> wrongPaginationParam = Arrays.asList(
            ParamName.ORDER.getParamName(),
            ParamName.SORT_FIELD.getParamName(),
            ParamName.LIMIT.getParamName(),
            ParamName.OFFSET.getParamName());


    public static String createFilterQuery(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder(SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS);
        String searchQuery = searchingParams(filterParam);
        String sortQuery = sortingParams(filterParam);
        if (!searchQuery.trim().isEmpty()) {
            sb.append(WHERE).append(searchQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        sb.append(sortQuery);
        return sb.toString();
    }

    private static String searchingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        filterParam.entrySet().stream()
                .filter(e -> wrongPaginationParam.stream().noneMatch(w -> e.getKey().equals(w)))
                .forEach(e -> {
                    sb.append("g.").append(e.getKey());
                    sb.append(LIKE);
                    sb.append(e.getValue());
                    sb.append(END_OF_LIKE);
                    sb.append(AND);
                });
        return sb.toString();
    }

    private static String sortingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        String direction = filterParam.get(ParamName.ORDER.getParamName());
        String field = filterParam.get(ParamName.SORT_FIELD.getParamName());
        if (Objects.nonNull(direction) && Objects.nonNull(field)) {
            sb.append(ORDER_BY).append("g.").append(field).append(SPACE).append(direction);
        }
        return sb.toString();
    }
}