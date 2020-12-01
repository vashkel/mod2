package com.epam.esm.modelDTO.user;

import com.epam.esm.modelDTO.order.CreateOrderRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private Set<CreateOrderRequestDTO> orders = new HashSet<>();
}
