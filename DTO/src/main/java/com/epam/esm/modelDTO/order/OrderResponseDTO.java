package com.epam.esm.modelDTO.order;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.util.LocalDateTimeDeserializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class OrderResponseDTO extends RepresentationModel<OrderResponseDTO> {

    private Long Id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    private BigDecimal cost;

    private Set<GiftCertificateDTO> giftCertificates = new HashSet<>();

    private UserDTO user;
}
