package com.epam.esm.modelDTO;

import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private Set<OrderDTO> orders = new HashSet<>();
}
