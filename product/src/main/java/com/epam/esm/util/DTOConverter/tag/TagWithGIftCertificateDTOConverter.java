package com.epam.esm.util.DTOConverter.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.tag.TagWithGiftCertificateDTO;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;

public class TagWithGIftCertificateDTOConverter {

    public static TagWithGiftCertificateDTO converterToTagWithGiftCertificateDTO(Tag tag){
        TagWithGiftCertificateDTO tagWithGiftCertificateDTO = new TagWithGiftCertificateDTO();
        tagWithGiftCertificateDTO.setId(tag.getId());
        tagWithGiftCertificateDTO.setName(tag.getName());
        tag.getGiftCertificates().forEach(giftCertificate ->
                tagWithGiftCertificateDTO.addGiftCertificates(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        return tagWithGiftCertificateDTO;
    }
}
