package com.epam.esm.repository.util;

import java.util.HashMap;
import java.util.Map;

public class GiftCertificateParamFetcher {

    public static Map<String, String> fetchParams(CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery) {
        Map<String, String> availableParams = new HashMap<>();
        if (commonParamsGiftCertificateQuery.getName() != null) {
            availableParams.put("name", commonParamsGiftCertificateQuery.getName());
        }
        if (commonParamsGiftCertificateQuery.getTag_name() != null) {
            availableParams.put("tag_name", "tag." + commonParamsGiftCertificateQuery.getTag_name());
        }
        if (commonParamsGiftCertificateQuery.getOrder() != null) {
            availableParams.put("order", commonParamsGiftCertificateQuery.getOrder());
        }
        if (commonParamsGiftCertificateQuery.getSortField() != null) {
            availableParams.put("sort_field", commonParamsGiftCertificateQuery.getSortField());
        }
        if (commonParamsGiftCertificateQuery.getLimit() != null) {
            availableParams.put("limit", String.valueOf(commonParamsGiftCertificateQuery.getLimit()));
        }
        if (commonParamsGiftCertificateQuery.getOffset() != null) {
            availableParams.put("offset", String.valueOf(commonParamsGiftCertificateQuery.getOffset()));
        }
        return availableParams;
    }
}
