package com.epam.esm.entityDTO.giftcertificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class GiftCertificateWithTagsDTO implements Serializable {
    private static final long serialVersionUID = -1784150257366720793L;

    private Long id;
    private String name;
    private String Description;
    private Double price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateTime;
    private Duration duration;
    private List<TagDTO> tagsDTO = new ArrayList<>();


    public void addTag(TagDTO tag){
       tagsDTO.add(tag);
    }
    public static GiftCertificateWithTagsDTO convertToGiftCertificateWithTagsDTO(GiftCertificate giftCertificate){
        GiftCertificateWithTagsDTO gIftCertificateWithTagsDTO = new GiftCertificateWithTagsDTO();
        gIftCertificateWithTagsDTO.setId(giftCertificate.getId());
        gIftCertificateWithTagsDTO.setName(giftCertificate.getName());
        gIftCertificateWithTagsDTO.setDescription(giftCertificate.getDescription());
        gIftCertificateWithTagsDTO.setPrice(giftCertificate.getPrice());
        gIftCertificateWithTagsDTO.setCreateDate(giftCertificate.getCreateDate());
        gIftCertificateWithTagsDTO.setLastUpdateTime(giftCertificate.getLastUpdateTime());
        gIftCertificateWithTagsDTO.setDuration(giftCertificate.getDuration());
        giftCertificate.getTags().forEach(tag -> gIftCertificateWithTagsDTO.addTag(TagDTO.converterToTagDTO(tag)));
        return gIftCertificateWithTagsDTO;
    }
}
