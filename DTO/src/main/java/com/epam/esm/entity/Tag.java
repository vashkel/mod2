package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
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

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<GiftCertificate> giftCertificates = new HashSet<>();

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
