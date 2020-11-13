package com.epam.esm.entity;

import com.epam.esm.util.DurationDeserializer;
import com.epam.esm.util.DurationSerializer;
import com.epam.esm.util.LocalDateTimeDeserializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate implements Serializable{
    private static final long serialVersionUID = -1734150257366390793L;

    @NotNull(message = "Id can not be null")
    private Long id;
    @NotNull(message = "Please provide name")
    private String name;
    @NotNull(message = "Please provide description")
    private String description;
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
    private List<Tag> tags = new ArrayList<>();

    public GiftCertificate(Long id, String name, String description, Double price, LocalDateTime toLocalDateTime, LocalDateTime toLocalDateTime1, Duration duration) {
    }

}
