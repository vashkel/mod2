package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    /**
     * This method is used to return certificate by id
     *
     * @param id the id of certificate to be returned
     * @return Optional value of find Certificate
     */
    Optional<GiftCertificate> findById(Long id);

    /**
     * This method is used to return the list of certificates
     *
     * @return List of all certificates or empty List if
     * no certificates were found
     */
    Optional<List<GiftCertificate>> findAll(CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery);

    /**
     * This method is used to create the certificate
     *
     * @param giftCertificate the certificate to be created
     * @return the value of created certificate
     */
    Optional<GiftCertificate> create(GiftCertificate giftCertificate);

    /**
     * This method is used to delete the certificate by id
     *
     * @param giftCertificate the id of certificate to be deleted
     * @return value of deleted certificate
     */
    void delete(GiftCertificate giftCertificate);

    /**
     * This method is used to update the certificate
     *
     * @param giftCertificate the certificate to be updated
     * @return value of updated certificate
     */
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);

    /**
     * This method is used to return certificate by name
     *
     * @param name the name of certificate to be returned
     * @return on Optional with the value of find Certificate
     */
    Optional<List<GiftCertificate>> findByName(String name);
}
