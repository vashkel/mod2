package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag implements Serializable {
    private static final long serialVersionUID = -1784132457366390793L;

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<GiftCertificate> giftCertificates = new HashSet<>();

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
