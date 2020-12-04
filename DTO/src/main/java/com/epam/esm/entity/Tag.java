package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
@NamedQueries({
        @NamedQuery(name = "Tag.findByName", query = "FROM Tag WHERE name = :name"),
        @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t LEFT JOIN fetch t.giftCertificates g"),
        @NamedQuery(name = "Tag.findById", query = "FROM Tag WHERE id = :id"),
        @NamedQuery(name = "Tag.findAllTagsByCertificateId",
                query = "SELECT t FROM Tag t LEFT JOIN fetch t.giftCertificates g WHERE g.id =:id")})
public class Tag implements Serializable {
    private static final long serialVersionUID = -1784132457366390793L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
