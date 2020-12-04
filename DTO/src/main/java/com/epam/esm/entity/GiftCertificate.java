package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_certificate")
@NamedQueries({
        @NamedQuery(name = "GiftCertificate.findAll",
                query = "SELECT DISTINCT g FROM GiftCertificate g LEFT JOIN fetch g.tags t "),
        @NamedQuery(name = "GiftCertificate.findById",
                query = "SELECT g FROM GiftCertificate g LEFT JOIN FETCH g.tags t WHERE g.id = :id"),
        @NamedQuery(name = "GiftCertificate.DeleteById",
                query = "DELETE GiftCertificate WHERE id = :id"),
        @NamedQuery(name = "GiftCertificate.findByName",
                query = "FROM GiftCertificate WHERE name = :name")

})
public class GiftCertificate implements Serializable {
    private static final long serialVersionUID = -1734150257366390793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateTime;

    @Column(name = "duration", nullable = false)
    private Duration duration;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "gift_certificate_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    public GiftCertificate(Long id, String name, String description, Double price, LocalDateTime toLocalDateTime,
                           LocalDateTime toLocalDateTime1, Duration duration) {
    }

}
