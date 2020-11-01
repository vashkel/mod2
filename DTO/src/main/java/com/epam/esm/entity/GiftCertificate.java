package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
public class GiftCertificate implements Serializable{
    private static final long serialVersionUID = -1734150257366390793L;

    private Long id;
    private String name;
    private String Description;
    private Double price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateTime;
    private Duration duration;
    private List<Tag> tags = new ArrayList<>();


    public void addTag(Tag tag){
        tags.add(tag);
    }
}
