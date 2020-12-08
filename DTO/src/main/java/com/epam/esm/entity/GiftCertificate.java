package com.epam.esm.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
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

    @NotAudited
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "gift_certificate_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToMany(mappedBy = "giftCertificate", fetch = FetchType.LAZY)
    private List<Order> orders;

    public GiftCertificate(Long id, String name, String description, Double price, LocalDateTime toLocalDateTime,
                           LocalDateTime toLocalDateTime1, Duration duration) {
    }

}
