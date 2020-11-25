package com.epam.esm.util.DTOConverter.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.GiftCertificateDTO;
import com.epam.esm.modelDTO.TagDTO;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;

import java.util.HashSet;
import java.util.Set;

public class GiftCertificateDTOConverter {

    public static GiftCertificateDTO convertToGiftCertificateDTO(GiftCertificate giftCertificate) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateDTOCreator(giftCertificate);
        if (!giftCertificate.getTags().isEmpty()) {
            giftCertificate.getTags().forEach(tag -> giftCertificateDTO.getTags()
                    .add(TagDTOConverter.converterToTagDTO(tag)));
        }
        return giftCertificateDTO;
    }

    public static GiftCertificate convertFromGiftCertificateDTO(GiftCertificateDTO giftCertificateDTO) {
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

    public static GiftCertificateDTO convertToGiftCertificateDTOWithoutTag(GiftCertificate giftCertificate) {
        return giftCertificateDTOCreator(giftCertificate);
    }

    public static GiftCertificate convertFromGiftCertificateDTOWithoutTag(GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateCreator(giftCertificateDTO);
    }


    private static GiftCertificate giftCertificateCreator(GiftCertificateDTO certificateDTO) {
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

    private static GiftCertificateDTO giftCertificateDTOCreator(GiftCertificate giftCertificate) {
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
