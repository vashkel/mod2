package com.epam.esm.modelDTO.tag;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> implements Serializable {
    private static final long serialVersionUID = -1784150257337720793L;

    private Long id;
    private String name;
    private Set<GiftCertificateDTO> giftCertificates = new HashSet<>();

}
