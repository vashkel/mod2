package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import exception.RepositoryException;
import exception.ServiceException;

import java.util.List;

public interface GiftCertificateService {
     GiftCertificateDTO find(Long id) throws   ServiceException;
     List<GiftCertificateDTO> findAll() throws ServiceException;
     GiftCertificateDTO create( GiftCertificate giftCertificate) throws  ServiceException;
     boolean deleteById(Long id) throws ServiceException;
     long update (GiftCertificate giftCertificate) throws ServiceException;
     List<GiftCertificateWithTagsDTO> findCertificatesByTagName(String tagName) throws ServiceException;
     List<GiftCertificateWithTagsDTO> findGiftCertificateByPartName(String partName) throws ServiceException;
     List<GiftCertificateWithTagsDTO> getFilteredGiftCertificates(String sortBy, String orderBy) throws ServiceException;
}
