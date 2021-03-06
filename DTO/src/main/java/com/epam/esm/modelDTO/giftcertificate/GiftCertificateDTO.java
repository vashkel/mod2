package com.epam.esm.modelDTO.giftcertificate;

import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.util.DurationDeserializer;
import com.epam.esm.util.DurationSerializer;
import com.epam.esm.util.LocalDateTimeDeserializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> implements Serializable {
    private static final long serialVersionUID = -7284150257366390793L;

    @NotNull
    @Min(value = 1)
    private Long id;

    @NotBlank(message = "Please provide name")
    private String name;

    @NotBlank(message = "Please provide description")
    private String Description;

    @NotNull(message = "Please provide price")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "999.0")
    private BigDecimal price;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateTime;

    @JsonDeserialize(using = DurationDeserializer.class)
    @JsonSerialize(using = DurationSerializer.class)
    private Duration duration;

    @Valid
    private Set<TagDTO> tags = new HashSet<>();


}
