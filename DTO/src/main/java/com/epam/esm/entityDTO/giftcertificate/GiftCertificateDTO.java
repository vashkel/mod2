package com.epam.esm.entityDTO.giftcertificate;

import com.epam.esm.entity.GiftCertificate;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
@Data
public class GiftCertificateDTO implements Serializable {
    private static final long serialVersionUID = -7284150257366390793L;
    private Long id;
    private String name;
    private String Description;
    private Double price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateTime;
    private Duration duration;

    public static GiftCertificateDTO convertToGiftCertificateDTO(GiftCertificate giftCertificate){
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(giftCertificate.getId());
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        giftCertificateDTO.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDTO.setLastUpdateTime(giftCertificate.getLastUpdateTime());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());
        return giftCertificateDTO;
    }
}
