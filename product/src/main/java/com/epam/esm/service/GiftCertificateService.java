package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.NotValidParamsRequest;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
public interface GiftCertificateService {
    /**
     * This method is used to return certificate by id
     *
     * @param id the id of certificate to be returned
     * @return value of find Certificate
     * @throws com.epam.esm.exception.GiftCertificateNotFoundException if certificate was not found
     */
    GiftCertificateWithTagsDTO find(Long id) throws ServiceException;

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    List<GiftCertificateWithTagsDTO> findAll() throws ServiceException;

    /**
     * This method is used to create the certificate
     *
     * @param giftCertificate the certificate to be created
     * @return true if certificate was created, false if it were not
     */
    GiftCertificateDTO create(GiftCertificate giftCertificate) throws ServiceException;

    /**
     * This method is used to delete the certificate by id
     *
     * @param id the id of certificate to be deleted
     * @return true if certificate was deleted, false if it were not
     * @throws com.epam.esm.exception.GiftCertificateNotFoundException if certificate does not exist
     */
    boolean deleteById(Long id) throws ServiceException;

    /**
     * This method is used to update the certificate
     *
     * @param giftCertificate the certificate to be updated
     * @return updated GiftCertificateDTO
     * @throws com.epam.esm.exception.GiftCertificateNotFoundException if certificate does not exist
     */
    GiftCertificateDTO update(GiftCertificate giftCertificate, Long id) throws ServiceException;

    /**
     * This method is used to return the list of certificates by name
     * of tag
     *
     * @return List of all certificates by tag name or empty List if
     * no certificates were found
     */
    List<GiftCertificateWithTagsDTO> findCertificatesByTagName(String tagName) throws ServiceException;

    /**
     * This method is used to return the list of certificates by part of name
     * certificate
     *
     * @return List of all certificates by part of name or empty List if
     * no certificates were found
     */
    List<GiftCertificateWithTagsDTO> findGiftCertificateByPartName(String partName) throws ServiceException;

    /**
     * This method is used to
     * sort the certificates by name asc/desc, date asc/date and find the certificates
     * with tag
     *
     * @param sortBy  name tag for searching in certificateList. (can be Null)
     * @param orderBy the sorting type selection. Possible values: asc, desc ,null
     * @return List of sorted certificates, or unsorted List if received orderBy
     * does not exist(or null) or received certificates is null
     */
    List<GiftCertificateWithTagsDTO> getFilteredGiftCertificates(String sortBy, String orderBy) throws ServiceException;

    List<GiftCertificateWithTagsDTO> getFilteredListCertificates(Map<String, String> filterParam) throws ServiceException, RepositoryException;
}
