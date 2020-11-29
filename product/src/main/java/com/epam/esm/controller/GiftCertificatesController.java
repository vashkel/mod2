package com.epam.esm.controller;

import com.epam.esm.modelDTO.GiftCertificateDTO;
import com.epam.esm.modelDTO.GiftCertificatePatchDTO;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<GiftCertificateDTO>> giftCertificates(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort_field", required = false) String sortField,
            @RequestParam(name = "tag_name", required = false) String tag_name,
            @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "8") int limit
    ) {
        CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery = initCommonParamsQuery(name, tag_name, sortField, order, offset, limit);
        List<GiftCertificateDTO> giftCertificatesDTOS = giftCertificateService.findAll(commonParamsGiftCertificateQuery);
        for(GiftCertificateDTO giftCertificateDTO: giftCertificatesDTOS){
            addLinks(giftCertificateDTO);
        };
        return ResponseEntity.ok().body(giftCertificatesDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> giftCertificate(@PathVariable("id") @Min(value = 1) long id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.find(id);
        addLinks(giftCertificateDTO);
        return ResponseEntity.ok().body(giftCertificateDTO);
    }

    @PostMapping
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
    public ResponseEntity<GiftCertificateDTO> updateCertificate(@PathVariable Long id, @RequestBody
                                                                @Valid GiftCertificateDTO giftCertificateDTO) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificateDTO, id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<GiftCertificatePatchDTO> updateCertificate(
            @PathVariable Long id, @RequestBody GiftCertificatePatchDTO giftCertificatePatchDTO){
        return ResponseEntity.ok(giftCertificateService.updatePatch(giftCertificatePatchDTO, id));
    }

    private void addLinks(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.getTags().forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class)
                .getTag(tagDTO.getId())).withSelfRel()));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificatesController.class)
                .giftCertificate(giftCertificateDTO.getId())).withSelfRel());
    }

    private CommonParamsGiftCertificateQuery initCommonParamsQuery(String name, String tag_name, String sortField, String order, int offset, int limit) {
        CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery = new CommonParamsGiftCertificateQuery();
        commonParamsGiftCertificateQuery.setName(name);
        commonParamsGiftCertificateQuery.setTag_name(tag_name);
        commonParamsGiftCertificateQuery.setOrder(order);
        commonParamsGiftCertificateQuery.setSortField(sortField);
        commonParamsGiftCertificateQuery.setOffset(offset);
        commonParamsGiftCertificateQuery.setLimit(limit);
        return commonParamsGiftCertificateQuery;
    }
}
