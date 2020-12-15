package com.epam.esm.modelDTO.giftcertificate;

import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.util.DurationDeserializer;
import com.epam.esm.util.DurationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificatePatchDTO implements Serializable {
    private static final long serialVersionUID = -7280296857366390793L;

    @NotNull
    @Min(value = 1)
    private Long id;

    private String name;
    @Size(min = 1, max = 180)
    private String description;
    @Min(value = 1)
    private BigDecimal price;

    @JsonDeserialize(using = DurationDeserializer.class)
    @JsonSerialize(using = DurationSerializer.class)
    private Duration duration;

    private Set<TagDTO> tags = new HashSet<>();
}
