package com.epam.esm.controller;

import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<TagDTO>> getTags(
            @RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "8") @Min(value = 1) int limit) {
        List<TagDTO> tagsDTO = tagService.findAll(offset, limit);
        tagsDTO.forEach(this::addLinks);
        return ResponseEntity.ok().body(tagsDTO);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<TagDTO> getTag(
            @PathVariable("id") @Min(value = 1, message = "id must be 1 or grater then 1") Long id) {
        TagDTO tagDTO = tagService.findById(id);
        addLinks(tagDTO);
        return ResponseEntity.ok().body(tagDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TagDTO> createTag(@RequestBody @Valid TagDTO tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tag));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteTag(
            @PathVariable("id") @Min(value = 1, message = "id must be 1 or grater then 1") Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void addLinks(TagDTO tagDTO) {
        tagDTO.getGiftCertificates().forEach(giftCertificateDTO -> giftCertificateDTO.add(WebMvcLinkBuilder
                .linkTo(methodOn(GiftCertificatesController.class)
                        .giftCertificate(giftCertificateDTO.getId())).withSelfRel()));
        tagDTO.add(WebMvcLinkBuilder.linkTo(methodOn(TagController.class).getTag(tagDTO.getId())).withSelfRel());
    }

    @GetMapping("/popular-tag")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<TagDTO> findMostPopularTagWithHighestPriceOfOrders() {
        return ResponseEntity.ok(tagService.findMostPopularTagWithHighestPriceOfOrders());
    }
}
