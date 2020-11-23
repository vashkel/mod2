package com.epam.esm.service;


import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;

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
    GiftCertificateDTO find(Long id);

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    List<GiftCertificateDTO> findAll(Map<String, String> filterParam);

    /**
     * This method is used to create the certificate
     *
     * @param giftCertificateDTO the certificate to be created
     * @return true if certificate was created, false if it were not
     */
    GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO);

    /**
     * This method is used to delete the certificate by id
     *
     * @param id the id of certificate to be deleted
     * @return true if certificate was deleted, false if it were not
     * @throws com.epam.esm.exception.GiftCertificateNotFoundException if certificate does not exist
     */
    void deleteById(Long id);

    /**
     * This method is used to update the certificate
     *
     * @param giftCertificate the certificate to be updated
     * @return updated GiftCertificateDTO
     * @throws com.epam.esm.exception.GiftCertificateNotFoundException if certificate does not exist
     */
    GiftCertificateDTO update(GiftCertificateDTO giftCertificate, Long id);

    /**
     * This method is used to return the list of certificates by name
     * of tag
     *
     * @return List of all certificates by tag name or empty List if
     * no certificates were found
     */
    List<GiftCertificateDTO> findCertificatesByTagName(String tagName);

}
