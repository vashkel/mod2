package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;

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
    GiftCertificateWithTagsDTO find(Long id);

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    List<GiftCertificateWithTagsDTO> findAll(Map<String, String> filterParam);

    /**
     * This method is used to create the certificate
     *
     * @param giftCertificateWithTagsDTO the certificate to be created
     * @return true if certificate was created, false if it were not
     */
    GiftCertificateWithTagsDTO create(GiftCertificateWithTagsDTO giftCertificateWithTagsDTO);

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
    GiftCertificateDTO update(GiftCertificate giftCertificate, Long id);

    /**
     * This method is used to return the list of certificates by name
     * of tag
     *
     * @return List of all certificates by tag name or empty List if
     * no certificates were found
     */
    List<GiftCertificateWithTagsDTO> findCertificatesByTagName(String tagName);

    /**
     * This method is used to
     * sort the certificates by name asc/desc, date asc/date and find the certificates
     * with tag
     *
     * @param filterParam searching params(can be Null)
     * @return List of sorted certificates, or unsorted List if received orderBy
     * does not exist(or null) or received certificates is null
     */
    List<GiftCertificateWithTagsDTO> getFilteredListCertificates(Map<String, String> filterParam);
}
