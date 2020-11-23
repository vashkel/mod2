package com.epam.esm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order implements Serializable {
    private static final long serialVersionUID = -6684132457366390793L;

    @Id
    private Long Id;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "cost")
    private BigDecimal cost;


}
