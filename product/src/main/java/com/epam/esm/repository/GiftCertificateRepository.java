package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateRepository {
    /**
     * This method is used to return certificate by id
     *
     * @param id the id of certificate to be returned
     * @return Optional value of find Certificate
     */
    Optional<GiftCertificate> findById(Long id) throws RepositoryException;

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    List findAll() throws RepositoryException;

    /**
     * This method is used to create the certificate
     *
     * @param giftCertificate the certificate to be created
     * @return the value of created certificate
     */
    Optional<GiftCertificate> create(GiftCertificate giftCertificate) throws RepositoryException;

    /**
     * This method is used to delete the certificate by id
     *
     * @param id the id of certificate to be deleted
     * @return value of deleted certificate
     */
    boolean delete(Long id) throws RepositoryException;

    /**
     * This method is used to update the certificate
     *
     * @param giftCertificate the certificate to be updated
     * @return value of updated certificate
     */
    boolean update(GiftCertificate giftCertificate) throws RepositoryException;

    /**
     * This method is used to return the list of certificates by name
     * of tag
     *
     * @return List of all certificates by tag name or empty List if
     * no certificates were found
     */
    List<GiftCertificate> findGiftCertificatesByTagName(String tag) throws RepositoryException;

    /**
     * This method is used to return the list of certificates by part of name
     * certificate
     *
     * @return List of all certificates by part of name or empty List if
     * no certificates were found
     */
    List<GiftCertificate> findGiftCertificateByPartName(String partName) throws RepositoryException;

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
    List<GiftCertificate> getSortedGiftCertificates(String sortBy, String orderBy) throws RepositoryException;

    List<GiftCertificate> filterCertificate(Map<String, String> filterParam) throws RepositoryException;

    /**
     * This method is used to return certificate by name
     *
     * @param name the name of certificate to be returned
     * @return on Optional with the value of find Certificate
     */
    Optional<GiftCertificate> findByName(String name) throws RepositoryException;
}
