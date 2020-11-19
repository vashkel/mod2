package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificatesController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> giftCertificates(@RequestParam Map<String,String> filterParam) throws ServiceException {
        if (filterParam.isEmpty()) {
            return ResponseEntity.ok().body(giftCertificateService.findAll());
        }
        return ResponseEntity.ok().body(giftCertificateService.getFilteredListCertificates(filterParam));
    }

    @GetMapping("{id}")
    public ResponseEntity<GiftCertificateWithTagsDTO> giftCertificate(@PathVariable("id") long id) throws ServiceException {
        return ResponseEntity.ok().body(giftCertificateService.find(id));
    }

    @PostMapping()
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(@Valid @RequestBody GiftCertificate giftCertificate)
            throws ServiceException {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.create(giftCertificate);
        if (giftCertificateDTO == null) {
            ResponseEntity.notFound();
        }
            return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.create(giftCertificate));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> deleteCertificate(@PathVariable("id") Long id) throws ServiceException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> updateCertificate(@PathVariable Long id, @Valid
    @RequestBody GiftCertificate giftCertificate) throws ServiceException {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificate, id));
    }

    @GetMapping("/tag_name/{tag_name}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificatesByTag
            (@PathVariable(name = "tag_name") String tagName) throws ServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findCertificatesByTagName(tagName));
    }

}
