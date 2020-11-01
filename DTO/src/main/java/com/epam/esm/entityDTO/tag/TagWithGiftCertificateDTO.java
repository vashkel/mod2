package com.epam.esm.entityDTO.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TagWithGiftCertificateDTO implements Serializable {
    private static final long serialVersionUID = -1784150257366720793L;

    private Long id;
    private String name;
    private List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

    private void addGiftCertificates(GiftCertificateDTO giftCertificateDTO){
       giftCertificatesDTO.add(giftCertificateDTO);
    }

    public static TagWithGiftCertificateDTO converterToTagWithGiftCertificateDTO(Tag tag){
        TagWithGiftCertificateDTO tagWithGiftCertificateDTO = new TagWithGiftCertificateDTO();
        tagWithGiftCertificateDTO.setId(tag.getId());
        tagWithGiftCertificateDTO.setName(tag.getName());
        tag.getGiftCertificates().forEach(giftCertificate ->
                tagWithGiftCertificateDTO.addGiftCertificates(GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificate)));
        return tagWithGiftCertificateDTO;
    }
}
