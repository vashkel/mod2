package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
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

    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;

    @GetMapping()
    public ResponseEntity<List<GiftCertificateDTO>> giftCertificates() throws ServiceException {
        if (giftCertificateService.findAll() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findAll());

    }

    @GetMapping("{id}")
    public ResponseEntity<GiftCertificateDTO> giftCertificate(@PathVariable("id") long id) throws ServiceException {
        if (giftCertificateService.find(id) == null) {
            return ResponseEntity.notFound().build();
        }
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
        if (giftCertificateService.find(id) == null) {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/tagname/{tagName}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificatesByTag(@PathVariable String tagName) throws ServiceException {
        if (giftCertificateService.findCertificatesByTagName(tagName) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findCertificatesByTagName(tagName));
    }

    @GetMapping("/partname/{partName}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificateByPartName(@PathVariable String partName) throws ServiceException {
        if (giftCertificateService.findCertificatesByTagName(partName) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findGiftCertificateByPartName(partName));
    }

    @GetMapping("/sortByName/{order}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificatesSortedByName(@PathVariable String order) throws ServiceException {
        if (giftCertificateService.findGiftCertificatesSortedByName(order) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findGiftCertificatesSortedByName(order));
    }
    @GetMapping("/sortByDate/{order}")
    public ResponseEntity<List<GiftCertificateWithTagsDTO>> findGiftCertificatesSortedByDate(@PathVariable String order) throws ServiceException{
        if(giftCertificateService.findGiftCertificatesSortedByName(order) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.findGiftCertificatesSortedByDate(order));
    }

}
