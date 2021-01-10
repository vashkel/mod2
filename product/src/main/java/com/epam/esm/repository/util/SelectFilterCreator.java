package com.epam.esm.repository.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectFilterCreator {

    private static final String WHERE = " where ";
    private static final String AND = " and ";
    private static final String END_OF_LIKE = "%' ";
    private static final String LIKE = " like '%";
    private static final String ORDER_BY = " order by ";
    private static final String SPACE = " ";
    private static final List<String> skipSortingParams = Arrays.asList(
            GiftCertificateParamName.ORDER.getParamName(),
            GiftCertificateParamName.SORT_FIELD.getParamName(),
            GiftCertificateParamName.LIMIT.getParamName(),
            GiftCertificateParamName.OFFSET.getParamName());

    public static String createFilterQuery(Map<String, String> availableParams, String query) {
        StringBuilder sb = new StringBuilder(query);
        trimAllParams(availableParams);
        String searchQuery = searchingParams(availableParams);
        String sortQuery = sortingParams(availableParams);
        if (!searchQuery.trim().isEmpty()) {
            sb.append(WHERE).append(searchQuery);
            sb.delete(sb.length() - AND.length(), sb.length());
        }
        sb.append(sortQuery);
        return sb.toString();
    }

    private static void trimAllParams(Map<String, String> availableParams) {
        for (Map.Entry<String, String> param : availableParams.entrySet()) {
            String trimmedParam = param.getValue().trim();
            param.setValue(trimmedParam);
        }
    }

    private static String searchingParams(Map<String, String> availableParams) {
        StringBuilder sb = new StringBuilder();
        availableParams.entrySet().stream()
                .filter(e -> skipSortingParams.stream().noneMatch(w -> e.getKey().equals(w)))
                .forEach(e -> {
                    sb.append(e.getKey());
                    sb.append(LIKE);
                    sb.append(e.getValue());
                    sb.append(END_OF_LIKE);
                    sb.append(AND);
                });
        return sb.toString();
    }

    private static String sortingParams(Map<String, String> availableParams) {
        StringBuilder sb = new StringBuilder();
        String direction = availableParams.get(GiftCertificateParamName.ORDER.getParamName());
        String sortField = availableParams.get(GiftCertificateParamName.SORT_FIELD.getParamName());
        if (Objects.nonNull(direction) && Objects.nonNull(sortField)) {
            sb.append(ORDER_BY).append(sortField).append(SPACE).append(direction);
        }
        return sb.toString();
    }

}