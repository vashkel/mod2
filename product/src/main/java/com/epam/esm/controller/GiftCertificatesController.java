package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificatesController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> giftCertificates(
            @RequestParam Map<String, String> filterParam) {

        return ResponseEntity.ok().body(giftCertificateService.findAll(filterParam));
    }

    @GetMapping("{id}")
    public ResponseEntity<GiftCertificateWithTagsDTO> giftCertificate(@PathVariable("id") @Min(value = 1) long id) {
        GiftCertificateWithTagsDTO giftCertificateWithTagsDTO = giftCertificateService.find(id);
        addLinks(giftCertificateWithTagsDTO);
        return ResponseEntity.ok().body(giftCertificateWithTagsDTO);
    }

    private void addLinks(GiftCertificateWithTagsDTO giftCertificateWithTagsDTO) {
        giftCertificateWithTagsDTO.getTags().forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class)
                .getTag(tagDTO.getId())).withSelfRel()));
        giftCertificateWithTagsDTO.add(linkTo(methodOn(GiftCertificatesController.class).giftCertificate(giftCertificateWithTagsDTO.getId())).withSelfRel());
    }


    @PostMapping()
    public ResponseEntity<GiftCertificateWithTagsDTO> createGiftCertificate(@Valid @RequestBody GiftCertificateWithTagsDTO certificateWithTagsDTO) {
        GiftCertificateWithTagsDTO giftCertificateWithTagsDTO = giftCertificateService.create(certificateWithTagsDTO);
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
    public ResponseEntity<GiftCertificateDTO> updateCertificate(@PathVariable Long id, @Valid
    @RequestBody GiftCertificate giftCertificate) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificate, id));
    }

    @GetMapping("/tag_name/{tag_name}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificatesByTag
            (@PathVariable(name = "tag_name") String tagName) {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findCertificatesByTagName(tagName));
    }

}
