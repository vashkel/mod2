package com.epam.esm.entity;

import com.epam.esm.util.DurationDeserializer;
import com.epam.esm.util.DurationSerializer;
import com.epam.esm.util.LocalDateTimeDeserializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GiftCertificate implements Serializable{
    private static final long serialVersionUID = -1734150257366390793L;

    private Long id;
    private String name;
    private String Description;
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

    public void addTag(Tag tag){
        tags.add(tag);
    }
}
