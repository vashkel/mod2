package com.epam.esm.util.DTOConverter.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;

public class GiftCertificateDTOConverter {

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
