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
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> implements Serializable {
    private static final long serialVersionUID = -7284150257366390793L;
   
    private Long id;

    @NotNull(message = "Please provide name")
    private String name;

    @NotNull(message = "Please provide description")
    private String Description;

    @NotNull(message = "Please provide price")
    private Double price;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateTime;

    @JsonDeserialize(using = DurationDeserializer.class)
    @JsonSerialize(using = DurationSerializer.class)
    private Duration duration;

    private Set<TagDTO> tags = new HashSet<>();


}
