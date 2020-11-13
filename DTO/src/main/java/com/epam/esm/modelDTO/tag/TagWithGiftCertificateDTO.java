package com.epam.esm.modelDTO.tag;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
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

    public void addGiftCertificates(GiftCertificateDTO giftCertificateDTO) {
        giftCertificatesDTO.add(giftCertificateDTO);
    }
}
