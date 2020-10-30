package com.epam.esm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = -1784132457366390793L;

    private long id;
    private String name;
    private List<GiftCertificate> giftCertificates = new ArrayList<>();

    public void addGiftCertificate(GiftCertificate giftCertificate){
        giftCertificates.add(giftCertificate);
    }

}
