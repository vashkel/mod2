package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.GiftCertificateFilterInfo;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.NotValidParamsRequest;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {


    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;

        this.tagRepository = tagRepository;
    }

    @Override
    public GiftCertificateDTO find(Long id) throws ServiceException {
        try {
            GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
            if (giftCertificate == null) {
                throw new GiftCertificateNotFoundException("certificate not found");
            }
            return GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificateRepository.findById(id));
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
            giftCertificates.forEach(giftCertificate -> giftCertificateDTOList.add(GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificate)));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown during find all gift certificates : ", e);
        }
        return giftCertificateDTOList;
    }

    @Override
    public GiftCertificateDTO create(GiftCertificate giftCertificate) throws ServiceException {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateTime(LocalDateTime.now());
        giftCertificate.setDuration(Duration.ofDays(30));
        try {
            return GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificateRepository.create(giftCertificate));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown create gift certificate : ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            GiftCertificate createdCertificate = giftCertificateRepository.findById(id);
            if (createdCertificate == null) {
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
            GiftCertificate certificate = giftCertificateRepository.findById(id);
            if (certificate != null) {
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
            giftCertificateWithTagsDTOS.add(GiftCertificateWithTagsDTO.convertToGiftCertificateWithTagsDTO(giftCertificate));
        }
        return giftCertificateWithTagsDTOS;
    }
    @Override
    public List<GiftCertificateWithTagsDTO> getFilteredGiftCertificates(String sortBy, String orderBy) throws ServiceException {
        GiftCertificateFilterInfo correctSortParam = GiftCertificateFilterInfo.checkSortParams(sortBy);
        GiftCertificateFilterInfo correctOrderParam = GiftCertificateFilterInfo.checkOrderParams(orderBy);
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
