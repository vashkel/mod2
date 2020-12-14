package com.epam.esm.repository.util;

import java.util.Arrays;
import java.util.List;

public enum GiftCertificateParamName {
    NAME("name"),
    TAG_NAME("tag_name"),
    ORDER("order"),
    SORT_FIELD("sort_field"),
    LIMIT("limit"),
    OFFSET("offset");

    private String paramName;
    private final static List<String> possibleOrderParamValue = Arrays.asList("asc", "desc");
    private final static List<String> possibleSortFieldParamValue = Arrays.asList("name", "create_date");
    private final static List<String> possibleCommonParamsQuery =
            Arrays.asList("name", "tag_name", "order", "sort_field", "limit", "offset");

    public static List<String> getPossibleSortFieldParamValue() {
        return possibleSortFieldParamValue;
    }

    public static List<String> getPossibleOrderParamValue() {
        return possibleOrderParamValue;
    }

    public static List<String> getPossibleCommonParamsQuery() {
        return possibleCommonParamsQuery;
    }

    GiftCertificateParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
