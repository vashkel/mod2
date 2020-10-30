package com.epam.esm.entityDTO.giftcertificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class GIftCertificateWithTagsDTO implements Serializable {
    private static final long serialVersionUID = -1784150257366720793L;

    private long id;
    private String name;
    private String Description;
    private double price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateTime;
    private Duration duration;
    private List<TagDTO> tagsDTO;


    public void addTag(TagDTO tag){
       tagsDTO.add(tag);
    }
    public static GIftCertificateWithTagsDTO convertToGiftCertificateWithTagsDTO(GiftCertificate giftCertificate){
        GIftCertificateWithTagsDTO gIftCertificateWithTagsDTO = new GIftCertificateWithTagsDTO();
        gIftCertificateWithTagsDTO.setId(giftCertificate.getId());
        gIftCertificateWithTagsDTO.setName(giftCertificate.getName());
        gIftCertificateWithTagsDTO.setDescription(giftCertificate.getDescription());
        gIftCertificateWithTagsDTO.setPrice(giftCertificate.getPrice());
        gIftCertificateWithTagsDTO.setCreateDate(giftCertificate.getCreateDate());
        gIftCertificateWithTagsDTO.setLastUpdateTime(giftCertificate.getLastUpdateTime());
        gIftCertificateWithTagsDTO.setDuration(giftCertificate.getDuration());
        for (Tag tag : giftCertificate.getTags()){
            gIftCertificateWithTagsDTO.addTag(TagDTO.converterToTagDTO(tag));
        }
        return gIftCertificateWithTagsDTO;
    }
}
