package com.epam.esm.util.DTOConverter.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;

import java.util.ArrayList;
import java.util.List;

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
        giftCertificate.getTags().forEach(tag -> gIftCertificateWithTagsDTO.getTags().add(TagDTOConverter.converterToTagDTO(tag)));
        return gIftCertificateWithTagsDTO;
    }

    public static List<GiftCertificateWithTagsDTO> convertListToGiftCertificatesWithTagsDTO(List giftCertificates){
        List<GiftCertificateWithTagsDTO> giftCertificateWithTagsDTO = new ArrayList<>();
            giftCertificates.forEach(giftCertificate-> giftCertificateWithTagsDTO.add(convertToGiftCertificateWithTagsDTO((GiftCertificate) giftCertificate)));
        return giftCertificateWithTagsDTO;
    }

    public static GiftCertificate convertToGiftCertificate(GiftCertificateWithTagsDTO giftCertificateDTO) {
        GiftCertificate gIftCertificate = new GiftCertificate();
        gIftCertificate.setId(giftCertificateDTO.getId());
        gIftCertificate.setName(giftCertificateDTO.getName());
        gIftCertificate.setDescription(giftCertificateDTO.getDescription());
        gIftCertificate.setPrice(giftCertificateDTO.getPrice());
        gIftCertificate.setCreateDate(giftCertificateDTO.getCreateDate());
        gIftCertificate.setLastUpdateTime(giftCertificateDTO.getLastUpdateTime());
        gIftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificateDTO.getTags().forEach(tagDTO -> gIftCertificate.getTags().add(TagDTOConverter.convertFromTagDTOToTag(tagDTO)));
        return gIftCertificate;
    }
}
