package com.epam.esm.modelDTO.order;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class OrderDTO {
    @NotNull(message = "Please provide id of user")
    Long userId;

    List<GiftCertificateDTO> giftCertificates;

    @JsonCreator
    public OrderDTO(@JsonProperty("userId") Long userId, @JsonProperty("giftCertificates") List <GiftCertificateDTO> giftCertificates) {
        this.userId = userId;
        this.giftCertificates = giftCertificates;
    }
}
