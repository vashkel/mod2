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
import com.epam.esm.util.ParamName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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

    @Transactional(readOnly = true)
    @Override
    public GiftCertificateWithTagsDTO find(Long id) throws ServiceException {
        try {
            Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
            if (!giftCertificate.isPresent()) {
                throw new GiftCertificateNotFoundException("certificate not found");
            }
            return GiftCertificateWithTagsDTOConverter.convertToGiftCertificateWithTagsDTO(giftCertificateRepository.findById(id).get());
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown during find gift certificate : ", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<GiftCertificateWithTagsDTO> findAll() throws ServiceException {
        try {
            return checkCertificateListAndFillWithTags(giftCertificateRepository.findAll());
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown during find all gift certificates : ", e);
        }
    }

    @Override
    public GiftCertificateDTO create(GiftCertificate giftCertificate) throws ServiceException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        GiftCertificateDTO giftCertificateDTO;
        try {
            Optional<GiftCertificate> createdCertificate = giftCertificateRepository.findByName(giftCertificate.getName());
            if (createdCertificate.isPresent()){
                return GiftCertificateDTOConverter.convertToGiftCertificateDTO(createdCertificate.get());
            }
            giftCertificate.setCreateDate(LocalDateTime.now());
            giftCertificate.setLastUpdateTime(LocalDateTime.now());
            giftCertificate.setDuration(Duration.ofDays(30));
            for (Tag insertedTag : giftCertificate.getTags()) {
                long tagID = tagRepository.create(insertedTag);
                insertedTag.setId(tagID);
            }
            giftCertificateDTO = GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificateRepository.create(giftCertificate).get());
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
    public GiftCertificateDTO update(GiftCertificate giftCertificate, Long id) throws ServiceException {
        GiftCertificateDTO giftCertificateDTO = null;
        try {
            Optional<GiftCertificate> certificate = giftCertificateRepository.findById(id);
            if (!certificate.isPresent()) {
                throw new GiftCertificateNotFoundException("gift certification not found");
            }
            giftCertificate.setId(id);
            giftCertificate.setLastUpdateTime(LocalDateTime.now());
            if (giftCertificateRepository.update(giftCertificate)) {
                giftCertificateDTO = GiftCertificateDTOConverter
                        .convertToGiftCertificateDTO(giftCertificateRepository.findById(id).get());
            }
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown update gift certificate : ", e);
        }
        return giftCertificateDTO;
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
                giftCertificate.getTags().add(tag);
            }
            giftCertificateWithTagsDTOS.add(GiftCertificateWithTagsDTOConverter.convertToGiftCertificateWithTagsDTO(giftCertificate));
        }
        return giftCertificateWithTagsDTOS;
    }

    @Override
    public List<GiftCertificateWithTagsDTO> getFilteredGiftCertificates(String sortBy, String orderBy) throws ServiceException {
        GiftCertificateFilterInfo correctSortParam = GiftCertificateFilterInfo.getSortParams(sortBy);
        GiftCertificateFilterInfo correctOrderParam = GiftCertificateFilterInfo.getOrderParams(orderBy);
        if (correctSortParam == null || correctOrderParam == null) {
            throw new NotValidParamsRequest("were entered not valid params");
        }
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.getSortedGiftCertificates(sortBy, orderBy);
            return checkCertificateListAndFillWithTags(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown get filtered gift certificates : ", e);
        }
    }

    @Override
    public List<GiftCertificateWithTagsDTO> getFilteredListCertificates(Map<String, String> filterParam) throws ServiceException, RepositoryException {
        deleteWrongSearchParam(filterParam);
        deleteWrongSortParam(filterParam);
        return checkCertificateListAndFillWithTags(giftCertificateRepository.filterCertificate(filterParam));
    }

    private void deleteWrongSortParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleDirectionParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.DIRECTION.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleFieldParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.FIELD.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
    }

    private void deleteWrongSearchParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(entry -> Arrays.stream(ParamName.values())
                .noneMatch(paramName -> paramName.getParamName().equals(entry.getKey())));
    }

}
