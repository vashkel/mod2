package com.epam.esm.util;

import java.util.Arrays;
import java.util.List;

public enum ParamName {
    NAME("name"),
    DESCRIPTION("description"),
    ORDER("order"),
    FIELD("field"),
    TAG("tags"),
    LIMIT("limit"),
    OFFSET("offset");

    private String paramName;
    private final static List<String> possibleDirectionParam = Arrays.asList("asc", "desc");
    private final static List<String> possibleFieldParam = Arrays.asList("name", " create_date");

    public static List<String> getPossibleFieldParam() {
        return possibleFieldParam;
    }

    public static List<String> getPossibleDirectionParam() {
        return possibleDirectionParam;
    }

    ParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
