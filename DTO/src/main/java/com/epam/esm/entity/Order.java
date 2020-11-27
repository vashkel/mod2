package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
@NamedQueries( {
        @NamedQuery(name = "Order.findById", query = "FROM Order WHERE id = :id"),
        @NamedQuery(name = "Order.findAll",
                    query = "SELECT o FROM Order o LEFT JOIN fetch o.giftCertificateSet c LEFT JOIN fetch o.user u")
})
public class Order implements Serializable {
    private static final long serialVersionUID = -6684132457366390793L;

    @Id
    private Long Id;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "cost")
    private BigDecimal cost;

    @ManyToMany
    @JoinTable(name = "order_gift_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private List<GiftCertificate> giftCertificateSet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
