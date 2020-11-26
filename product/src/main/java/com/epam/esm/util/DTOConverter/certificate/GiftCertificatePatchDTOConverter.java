package com.epam.esm.util.DTOConverter.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.GiftCertificatePatchDTO;
import com.epam.esm.modelDTO.TagDTO;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;

import java.util.HashSet;
import java.util.Set;

public class GiftCertificatePatchDTOConverter {

    public static GiftCertificatePatchDTO convertToGiftCertificateDTO(GiftCertificate giftCertificate) {
        GiftCertificatePatchDTO giftCertificateDTO = giftCertificateDTOCreator(giftCertificate);
        if (!giftCertificate.getTags().isEmpty()) {
            giftCertificate.getTags().forEach(tag -> giftCertificateDTO.getTags()
                    .add(TagDTOConverter.converterToTagDTO(tag)));
        }
        return giftCertificateDTO;
    }

    public static GiftCertificate convertFromGiftCertificatePatchDTO(GiftCertificatePatchDTO giftCertificateDTO) {
        Set<Tag> tags = new HashSet<>();
        GiftCertificate gIftCertificate = giftCertificateCreator(giftCertificateDTO);
        if (!giftCertificateDTO.getTags().isEmpty()) {
            for (TagDTO tagDTO : giftCertificateDTO.getTags()) {
                tags.add(TagDTOConverter.convertFromTagDTO(tagDTO));
            }
            gIftCertificate.setTags(tags);
        }
        return gIftCertificate;
    }

    public static GiftCertificatePatchDTO convertToGiftCertificateDTOWithoutTag(GiftCertificate giftCertificate) {
        return giftCertificateDTOCreator(giftCertificate);
    }

    public static GiftCertificate convertFromGiftCertificateDTOWithoutTag(GiftCertificatePatchDTO giftCertificateDTO) {
        return giftCertificateCreator(giftCertificateDTO);
    }


    private static GiftCertificate giftCertificateCreator(GiftCertificatePatchDTO certificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateDTO.getId());
        giftCertificate.setName(certificateDTO.getName());
        giftCertificate.setDescription(certificateDTO.getDescription());
        giftCertificate.setPrice(certificateDTO.getPrice());
        giftCertificate.setCreateDate(certificateDTO.getCreateDate());
        giftCertificate.setLastUpdateTime(certificateDTO.getLastUpdateTime());
        giftCertificate.setDuration(certificateDTO.getDuration());
        return giftCertificate;
    }

    private static GiftCertificatePatchDTO giftCertificateDTOCreator(GiftCertificate giftCertificate) {
        GiftCertificatePatchDTO giftCertificateDTO = new GiftCertificatePatchDTO();
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
