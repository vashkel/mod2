package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import exception.RepositoryException;
import exception.ServiceException;

import java.util.List;

public interface GiftCertificateService {
     GiftCertificateDTO find(Long id) throws JsonProcessingException, RepositoryException, ServiceException;
     List<GiftCertificateDTO> findAll() throws RepositoryException, JsonProcessingException, ServiceException;
     GiftCertificateDTO create( GiftCertificate giftCertificate) throws JsonProcessingException, ServiceException;
     void deleteById(Long id) throws ServiceException;
     long update (GiftCertificate giftCertificate) throws JsonProcessingException, ServiceException;
     List<GiftCertificate> findCertificatesByTagName(String tagName) throws ServiceException;
     List<GiftCertificate> findGiftCertificateByPartName(String partName);
     List<GiftCertificate> findGiftCertificatesSortedByNameASC();
     List<GiftCertificate> findGiftCertificatesSortedByNameDESC();
}
