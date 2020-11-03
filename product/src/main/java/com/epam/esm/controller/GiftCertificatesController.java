package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificatesController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateDTO>> giftCertificates() throws ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> giftCertificate(@PathVariable("id") long id) throws ServiceException {
        return ResponseEntity.ok().body(giftCertificateService.find(id));
    }

    @PostMapping()
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException {
        if (giftCertificateService.create(giftCertificate) == null) {
            ResponseEntity.notFound();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> deleteCertificate(@PathVariable("id") Long id) throws ServiceException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateCertificate(@RequestBody GiftCertificate giftCertificate, @PathVariable Long id) throws ServiceException {
        if (giftCertificateService.find(id) == null) {
            return ResponseEntity.notFound().build();
        }
        giftCertificate.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.update(giftCertificate));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>>  sortedGiftCertificatesWithTags(@RequestParam(value = "sort", required = false ) String sort,
                                                                                            @RequestParam(value = "order") String order) throws ServiceException {
        return ResponseEntity.ok().body(giftCertificateService.getFilteredGiftCertificates(sort, order));
    }

    @GetMapping("/tag-name/{tag-name}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificatesByTag(@PathVariable(name = "tag-name") String tagName) throws ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findCertificatesByTagName(tagName));
    }

    @GetMapping("/part-name/{part-name}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificateByPartName(@PathVariable(name = "part-name") String partName) throws ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findGiftCertificateByPartName(partName));
    }


}
