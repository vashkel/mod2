package com.epam.esm.entity;

import com.epam.esm.util.DurationDeserializer;
import com.epam.esm.util.DurationSerializer;
import com.epam.esm.util.LocalDateTimeDeserializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
                query = "FROM GiftCertificate WHERE id = :id"),
        @NamedQuery(name = "GiftCertificate.DeleteById",
                query = "DELETE GiftCertificate WHERE id = :id"),
        @NamedQuery(name = "GiftCertificate.findByName",
                query = "FROM GiftCertificate WHERE name = :name")

})
public class GiftCertificate implements Serializable  {
    private static final long serialVersionUID = -1734150257366390793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateTime;

    @JsonDeserialize(using = DurationDeserializer.class)
    @JsonSerialize(using = DurationSerializer.class)
    @Column(name = "duration", nullable = false)
    private Duration duration;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "gift_certificate_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    public GiftCertificate(Long id, String name, String description, Double price, LocalDateTime toLocalDateTime, LocalDateTime toLocalDateTime1, Duration duration) {
    }

}
