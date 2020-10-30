package com.epam.esm.entity;

import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
public class GiftCertificate implements Serializable{
    private static final long serialVersionUID = -1734150257366390793L;

    private long id;
    private String name;
    private String Description;
    private double price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateTime;
    private Duration duration;
    private List<Tag> tags = new ArrayList<>();


    public void addTag(Tag tag){
        tags.add(tag);
    }
}
