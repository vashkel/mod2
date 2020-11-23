package com.epam.esm.controller;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificatesController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateDTO>> giftCertificates(
            @RequestParam Map<String, String> filterParam) {
        List<GiftCertificateDTO> giftCertificatesDTO = giftCertificateService.findAll(filterParam);
        giftCertificatesDTO.forEach(this::addLinks);
        return ResponseEntity.ok().body(giftCertificatesDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> giftCertificate(@PathVariable("id") @Min(value = 1) long id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.find(id);
        addLinks(giftCertificateDTO);
        return ResponseEntity.ok().body(giftCertificateDTO);
    }

    @PostMapping()
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(@Valid @RequestBody GiftCertificateDTO certificateDTO) {
        GiftCertificateDTO giftCertificateWithTagsDTO = giftCertificateService.create(certificateDTO);
        if (giftCertificateWithTagsDTO == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateWithTagsDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> deleteCertificate(@PathVariable("id") @Min(value = 1) Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> updateCertificate(@PathVariable Long id,
                                                                @RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificateDTO, id));
    }

    @GetMapping("/tag_name/{tag_name}")
    public ResponseEntity<List<GiftCertificateDTO>> findGiftCertificatesByTag
            (@PathVariable(name = "tag_name") String tagName) {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findCertificatesByTagName(tagName));
    }

    private void addLinks(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.getTags().forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class)
                .getTag(tagDTO.getId())).withSelfRel()));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificatesController.class).giftCertificate(giftCertificateDTO.getId())).withSelfRel());
    }


}
