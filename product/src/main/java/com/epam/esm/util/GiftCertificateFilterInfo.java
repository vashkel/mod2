package com.epam.esm.util;

public enum GiftCertificateFilterInfo {
    NAME, DATE, ASC, DESC;

    public static GiftCertificateFilterInfo getSortParams(String input) {

        switch (input.toUpperCase()) {
            case "NAME":
                return NAME;
            case "DATE":
                return DATE;
            default:
                return null;
        }
    }

    public static GiftCertificateFilterInfo getOrderParams(String input) {

        switch (input.toUpperCase()) {
            case "ASC":
                return ASC;
            case "DESC":
                return DESC;
            default:
                return null;
        }
    }
}