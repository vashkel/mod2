package com.epam.esm.repository.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Validated
public class CommonParamsGiftCertificateQuery {

    private String name;
    private String order;
    private String tag_name;
    private String sortField;
    private String tags;
    @Min(value = 1)
    private Integer limit;
    @Min(value = 1)
    private Integer offset;

    public static Map<String, String> fetchParams(CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery) {
        Map<String, String> availableParams = new HashMap<>();
        if (commonParamsGiftCertificateQuery.getName() != null) {
            availableParams.put("c.name", commonParamsGiftCertificateQuery.getName());
        }
        if (commonParamsGiftCertificateQuery.getTag_name() != null) {
            availableParams.put("tag.name", commonParamsGiftCertificateQuery.getTag_name());
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
