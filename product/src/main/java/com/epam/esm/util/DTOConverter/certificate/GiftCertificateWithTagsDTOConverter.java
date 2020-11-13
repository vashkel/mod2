package com.epam.esm.util.DTOConverter.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;

public class GiftCertificateWithTagsDTOConverter {

    public static GiftCertificateWithTagsDTO convertToGiftCertificateWithTagsDTO(GiftCertificate giftCertificate){
        GiftCertificateWithTagsDTO gIftCertificateWithTagsDTO = new GiftCertificateWithTagsDTO();
        gIftCertificateWithTagsDTO.setId(giftCertificate.getId());
        gIftCertificateWithTagsDTO.setName(giftCertificate.getName());
        gIftCertificateWithTagsDTO.setDescription(giftCertificate.getDescription());
        gIftCertificateWithTagsDTO.setPrice(giftCertificate.getPrice());
        gIftCertificateWithTagsDTO.setCreateDate(giftCertificate.getCreateDate());
        gIftCertificateWithTagsDTO.setLastUpdateTime(giftCertificate.getLastUpdateTime());
        gIftCertificateWithTagsDTO.setDuration(giftCertificate.getDuration());
        giftCertificate.getTags().forEach(tag -> gIftCertificateWithTagsDTO.addTag(TagDTOConverter.converterToTagDTO(tag)));
        return gIftCertificateWithTagsDTO;
    }
}
