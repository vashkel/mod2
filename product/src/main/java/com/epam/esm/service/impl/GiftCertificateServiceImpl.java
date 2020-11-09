package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.NotValidParamsRequest;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateWithTagsDTOConverter;
import com.epam.esm.util.GiftCertificateFilterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;
    private PlatformTransactionManager txManager;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository, PlatformTransactionManager txManager) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.txManager = txManager;
    }

    @Override
    public GiftCertificateDTO find(Long id) throws ServiceException {
        try {
            Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
            if (!giftCertificate.isPresent()) {
                throw new GiftCertificateNotFoundException("certificate not found");
            }
            return GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificateRepository.findById(id).get());
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown during find gift certificate : ", e);
        }
    }

    @Override
    public List<GiftCertificateDTO> findAll() throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTOList = new ArrayList<>();
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
            if (giftCertificates.isEmpty()) {
                throw new GiftCertificateNotFoundException("gift Certificate not found");
            }
            giftCertificates.forEach(giftCertificate -> giftCertificateDTOList.add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown during find all gift certificates : ", e);
        }
        return giftCertificateDTOList;
    }

    @Override
    public GiftCertificateDTO create(GiftCertificate giftCertificate) throws ServiceException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateTime(LocalDateTime.now());
        giftCertificate.setDuration(Duration.ofDays(30));
        try {
            for (Tag insertedTag : giftCertificate.getTags()) {
                long tagID = tagRepository.create(insertedTag);
                insertedTag.setId(tagID);
            }
            GiftCertificateDTO giftCertificateDTO = GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificateRepository.create(giftCertificate).get());
            txManager.commit(status);
            return giftCertificateDTO;
        } catch (RepositoryException e) {
            txManager.rollback(status);
            throw new ServiceException("An exception was thrown create gift certificate : ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            Optional<GiftCertificate> createdCertificate = giftCertificateRepository.findById(id);
            if (!createdCertificate.isPresent()) {
                throw new GiftCertificateNotFoundException("gift certificate not found");
            }
            return giftCertificateRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown delete gift certificate : ", e);
        }
    }

    @Override
    public boolean update(GiftCertificate giftCertificate, Long id) throws ServiceException {
        try {
            Optional<GiftCertificate> certificate = giftCertificateRepository.findById(id);
            if (!certificate.isPresent()) {
                throw new GiftCertificateNotFoundException("gift certification not found");
            }
            giftCertificate.setId(id);
            giftCertificate.setLastUpdateTime(LocalDateTime.now());
            return giftCertificateRepository.update(giftCertificate);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown update gift certificate : ", e);
        }
    }

    @Override
    public List<GiftCertificateWithTagsDTO> findCertificatesByTagName(String tagName) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificatesByTagName(tagName);
            return checkCertificateListAndFillWithTags(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown find gift certificate by name of tag : ", e);
        }
    }

    @Override
    public List<GiftCertificateWithTagsDTO> findGiftCertificateByPartName(String partName) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificateByPartName(partName);
            return checkCertificateListAndFillWithTags(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown find gift certificate by part of name of tag : ", e);
        }
    }

    private List<GiftCertificateWithTagsDTO> checkCertificateListAndFillWithTags(List<GiftCertificate> giftCertificates) throws RepositoryException {
        List<GiftCertificateWithTagsDTO> giftCertificateWithTagsDTOS = new ArrayList<>();
        if (giftCertificates.isEmpty()) {
            throw new GiftCertificateNotFoundException("gift Certificate not found");
        }
        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = tagRepository.findAllTagsByCertificateId(giftCertificate.getId());
            for (Tag tag : tags) {
                giftCertificate.addTag(tag);
            }
            giftCertificateWithTagsDTOS.add(GiftCertificateWithTagsDTOConverter.convertToGiftCertificateWithTagsDTO(giftCertificate));
        }
        return giftCertificateWithTagsDTOS;
    }
    @Override
    public List<GiftCertificateWithTagsDTO> getFilteredGiftCertificates(String sortBy, String orderBy) throws ServiceException {
        GiftCertificateFilterInfo correctSortParam = GiftCertificateFilterInfo.getSortParams(sortBy);
        GiftCertificateFilterInfo correctOrderParam = GiftCertificateFilterInfo.getOrderParams(orderBy);
        if (correctSortParam == null && correctOrderParam == null) {
            throw new NotValidParamsRequest();
        }
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.getSortedGiftCertificates(sortBy, orderBy);
            return checkCertificateListAndFillWithTags(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown get filtered gift certificates : ", e);
        }
    }

}
