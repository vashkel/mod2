package com.epam.esm.modelDTO.order;

import com.epam.esm.util.LocalDateTimeDeserializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
public class UsersOrderDTO {

    Long orderId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    private BigDecimal cost;
}
