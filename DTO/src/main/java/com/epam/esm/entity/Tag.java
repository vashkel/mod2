package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = -1784132457366390793L;

    private Long id;
    private String name;
    private List<GiftCertificate> giftCertificates = new ArrayList<>();

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public void addGiftCertificate(GiftCertificate giftCertificate){
        giftCertificates.add(giftCertificate);
    }

}
