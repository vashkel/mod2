package com.epam.esm.modelDTO.security;

import com.epam.esm.modelDTO.order.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {

    private Long id;
    private String name;
}
