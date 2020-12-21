package com.epam.esm.controller;

import com.epam.esm.modelDTO.giftcertificate.GiftCertificateCreateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificatePatchDTO;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN','GUEST')")
    public ResponseEntity<List<GiftCertificateDTO>> giftCertificates(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort_field", required = false) String sortField,
            @RequestParam(name = "tag_name", required = false) String tag_name,
            @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "offset", required = false, defaultValue = "0") @Min(value = 0) int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "8") @Min(value = 1) int limit
    ) {
        CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery =
                initCommonParamsQuery(name, tag_name, sortField, order, offset, limit);
        List<GiftCertificateDTO> giftCertificatesDTOS = giftCertificateService.findAll(commonParamsGiftCertificateQuery);
        for (GiftCertificateDTO giftCertificateDTO : giftCertificatesDTOS) {
            addLinks(giftCertificateDTO);
        }
        ;
        return ResponseEntity.ok().body(giftCertificatesDTOS);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN','GUEST')")
    public ResponseEntity<GiftCertificateDTO> giftCertificate(@PathVariable("id") @Min(value = 1) long id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.find(id);
        addLinks(giftCertificateDTO);
        return ResponseEntity.ok().body(giftCertificateDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(
            @Valid @RequestBody GiftCertificateCreateDTO certificateDTO) {
        GiftCertificateDTO giftCertificateWithTagsDTO = giftCertificateService.create(certificateDTO);
        if (giftCertificateWithTagsDTO == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateWithTagsDTO);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDTO> deleteCertificate(
            @PathVariable("id") @Min(value = 1, message = "id must be 1 or grater then 1") Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDTO> updateCertificate(@RequestBody
                                                                @Valid GiftCertificateDTO giftCertificateDTO) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificateDTO));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDTO> updateCertificate(
            @RequestBody @Valid GiftCertificatePatchDTO giftCertificatePatchDTO) {
        return ResponseEntity.ok(giftCertificateService.updatePatch(giftCertificatePatchDTO));
    }

    private void addLinks(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.getTags().forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class)
                .getTag(tagDTO.getId())).withSelfRel()));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificatesController.class)
                .giftCertificate(giftCertificateDTO.getId())).withSelfRel());
    }

    private CommonParamsGiftCertificateQuery initCommonParamsQuery(String name, String tag_name, String sortField,
                                                                   String order, int offset, int limit) {
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
