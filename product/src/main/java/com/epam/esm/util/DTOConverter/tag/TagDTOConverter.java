package com.epam.esm.util.DTOConverter.tag;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.GiftCertificateDTO;
import com.epam.esm.modelDTO.TagDTO;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
public class TagDTOConverter {

    public static TagDTO converterToTagDTO(Tag tag) {
        Set<GiftCertificateDTO> giftCertificatesDTO = new HashSet<>();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        if (!tag.getGiftCertificates().isEmpty()){
            for (GiftCertificate giftCertificate: tag.getGiftCertificates()){
                giftCertificatesDTO.add(GiftCertificateDTOConverter
                        .convertToGiftCertificateDTOWithoutTag(giftCertificate));
            }
            tagDTO.setGiftCertificates(giftCertificatesDTO);
        }
        return tagDTO;

    }

    public static Tag convertFromTagDTO(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        if (!tagDTO.getGiftCertificates().isEmpty()){
            tagDTO.getGiftCertificates().forEach(giftCertificateDTO -> tag.getGiftCertificates()
                    .add(GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO)));
        }
        return tag;
    }
}
