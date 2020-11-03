package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import exception.RepositoryException;

import java.util.List;

public interface GiftCertificateRepository {
    GiftCertificate findGiftCertificateById(Long id) throws RepositoryException;
    List<GiftCertificate> findAllGiftCertificates() throws RepositoryException;
    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) throws RepositoryException;
    boolean delete(Long id) throws RepositoryException;
    long update (GiftCertificate giftCertificate) throws RepositoryException;
    List<GiftCertificate> findGiftCertificatesByTagName(String tag) throws RepositoryException;
    List<GiftCertificate> findGiftCertificateByPartName(String partName) throws RepositoryException;
    List<GiftCertificate> getFilteredGiftCertificates(String sortBy, String orderBy) throws RepositoryException;

}
