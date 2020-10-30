package com.epam.esm.entityDTO.tag;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TagWithGiftCertificateDTO implements Serializable {
    private static final long serialVersionUID = -1784150257366720793L;

    private long id;
    private String name;
    private List<GiftCertificateDTO> giftCertificatesDTO;

    private void addGiftCertificates(GiftCertificateDTO giftCertificateDTO){
       giftCertificatesDTO.add(giftCertificateDTO);
    }

    public static TagWithGiftCertificateDTO converterToTagWithGiftCertificateDTO(Tag tag){
        TagWithGiftCertificateDTO tagWithGiftCertificateDTO = new TagWithGiftCertificateDTO();
        tagWithGiftCertificateDTO.setId(tag.getId());
        tagWithGiftCertificateDTO.setName(tag.getName());
        for(GiftCertificate giftCertificate: tag.getGiftCertificates()){
            tagWithGiftCertificateDTO.addGiftCertificates(GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificate));
        }
        return tagWithGiftCertificateDTO;
    }
}
