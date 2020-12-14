package com.epam.esm.modelDTO.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderDTO {
    @NotNull(message = "Please provide id of user")
    Long userId;

    @NotNull(message = "Please enter id of gift certificate")
    @NotEmpty(message = "enter certificates, can not be empty")
    @Valid
    List<Long> giftCertificates;

    @JsonCreator
    public OrderDTO(@JsonProperty("userId") Long userId, @JsonProperty("giftCertificates") List<Long> giftCertificates) {
        this.userId = userId;
        this.giftCertificates = giftCertificates;
    }
}
