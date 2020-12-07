package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_order")
@NamedQueries({
        @NamedQuery(name = "Order.findById", query = "FROM Order WHERE id = :id"),
        @NamedQuery(name = "Order.findAll",
                query = "SELECT o FROM Order o LEFT JOIN fetch o.giftCertificate c LEFT JOIN fetch o.user u"),
        @NamedQuery(name = "Order.findUserOrders", query = "FROM Order where user.id = : userId")
})
public class Order implements Serializable {
    private static final long serialVersionUID = -6684132457366390793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "cost")
    private BigDecimal cost;

    @ManyToMany
    @JoinTable(name = "order_gift_certificate",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"))
    private List<GiftCertificate> giftCertificate = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

}
