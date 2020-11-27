package com.epam.esm.modelDTO;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long Id;
    private LocalDateTime createDate;
    private BigDecimal cost;
    private List<GiftCertificateDTO> giftCertificateSet;
    private User user;
}
