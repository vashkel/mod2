package com.epam.esm.repository.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommonParamsGiftCertificateQuery {

    private String name;
    private String order;
    private String tag_name;
    private String sortField;
    private String tags;
    private Integer limit;
    private Integer offset;

}
