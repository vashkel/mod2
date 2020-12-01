package com.epam.esm.modelDTO.order;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class OrderDTO {
    Long userId;
    List<GiftCertificateDTO> giftCertificates;

    @JsonCreator
    public OrderDTO(@JsonProperty("userId") Long userId, @JsonProperty("giftCertificates") List <GiftCertificateDTO> giftCertificates) {
        this.userId = userId;
        this.giftCertificates = giftCertificates;
    }
}
