package com.epam.esm.repository;

import com.epam.esm.util.ParamName;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectFilterCreator {

    public static String SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS = "SELECT DISTINCT  c.id, c.name AS name, c.description, " +
            "c.price, c.create_date AS create_date, c.last_update_date, c.duration FROM gift_certificate AS c LEFT JOIN  " +
            "gift_certificate_tags AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id ";

    private static final String HAVING = " having ";
    private static final String AND = " and ";
    private static final String END_OF_LIKE = "%' ";
    private static final String LIKE = " like '%";
    private static final String ORDER_BY = " order by ";
    private static final String SPACE = " ";
    private static final String LIMIT = " limit ";
    private static final String OFFSET = " offset ";
    private static final List<String> wrongPaginationParam = Arrays.asList(
            ParamName.ORDER.getParamName(),
            ParamName.FIELD.getParamName(),
            ParamName.LIMIT.getParamName(),
            ParamName.OFFSET.getParamName());


    public String createFilterQuery(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder(SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS);
        String searchQuery = searchingParams(filterParam);
        String sortQuery = sortingParams(filterParam);
//        String paginationQuery = paginationParams(filterParam);
        if (!searchQuery.trim().isEmpty()) {
            sb.append(HAVING).append(searchQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        sb.append(sortQuery);
//        sb.append(paginationQuery);
//        //delete comment
//        System.out.println("query = "+sb.toString());
        return sb.toString();
    }

    private String searchingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        filterParam.entrySet().stream()
                .filter(e-> wrongPaginationParam.stream().noneMatch(w -> e.getKey().equals(w)))
                .forEach(e -> {
                    sb.append(e.getKey());
                    sb.append(LIKE);
                    sb.append(e.getValue());
                    sb.append(END_OF_LIKE);
                    sb.append(AND);
                });
        return sb.toString();
    }

    private String sortingParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        String direction = filterParam.get(ParamName.ORDER.getParamName());
        String field = filterParam.get(ParamName.FIELD.getParamName());
        if (Objects.nonNull(direction) && Objects.nonNull(field)) {
            sb.append(ORDER_BY).append(field).append(SPACE).append(direction);
        }
        return sb.toString();
    }

    private String paginationParams(Map<String, String> filterParam) {
        StringBuilder sb = new StringBuilder();
        String limit = filterParam.get(ParamName.LIMIT.getParamName());
        String offset = filterParam.get(ParamName.OFFSET.getParamName());
        if (Objects.nonNull(limit) && Objects.nonNull(offset)) {
            sb.append(LIMIT).append(limit).append(SPACE).append(OFFSET).append(offset);
        }
        return sb.toString();
    }
}