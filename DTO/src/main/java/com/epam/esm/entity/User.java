package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findById", query = "FROM User WHERE id = :id "),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u LEFT JOIN fetch u.orders o")
})
public class User implements Serializable {
    private static final long serialVersionUID = -17888390793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();


}
