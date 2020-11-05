package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.repository.GiftCertificateRepository;
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


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;

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
                throw new GiftCertificateNotFoundException("certificate not found");
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
        List<GiftCertificateWithTagsDTO> giftCertificateWithTagsDTOList = new ArrayList<>();
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificatesByTagName(tagName);
            if (giftCertificates.isEmpty()) {
                throw new GiftCertificateNotFoundException("gift Certificate not found");
            }
            giftCertificateRepository.findGiftCertificatesByTagName(tagName).forEach(giftCertificate ->
                    giftCertificateWithTagsDTOList.add(GiftCertificateWithTagsDTO.convertToGiftCertificateWithTagsDTO(giftCertificate)));
            return giftCertificateWithTagsDTOList;
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown find gift certificate by name of tag : ", e);
        }
    }

    @Override
    public List<GiftCertificateWithTagsDTO> findGiftCertificateByPartName(String partName) throws ServiceException {
        List<GiftCertificateWithTagsDTO> giftCertificateWithTagsDTOS = new ArrayList<>();
        try {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificateByPartName(partName);
            if (giftCertificates.isEmpty()) {
                throw new GiftCertificateNotFoundException("gift Certificate not found");
            }
            giftCertificateRepository.findGiftCertificateByPartName(partName).forEach(giftCertificate ->
                    giftCertificateWithTagsDTOS.add(GiftCertificateWithTagsDTO.convertToGiftCertificateWithTagsDTO(giftCertificate)));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown find gift certificate by part of name of tag : ", e);
        }
        return giftCertificateWithTagsDTOS;
    }

    @Override
    public List<GiftCertificateWithTagsDTO> getFilteredGiftCertificates(String sortBy, String orderBy) throws ServiceException {
        List<GiftCertificateWithTagsDTO> giftCertificateWithTagsDTOS = new ArrayList<>();
        GiftCertificateFilterInfo correctSortParam = GiftCertificateFilterInfo.checkSortParams(sortBy);
        GiftCertificateFilterInfo correctOrderParam = GiftCertificateFilterInfo.checkOrderParams(orderBy);
        if (correctSortParam == null && correctOrderParam == null) {
            throw new NotValidParamsRequest();
        }
        try {
            giftCertificateRepository.getFilteredGiftCertificates(sortBy, orderBy)
                    .forEach(giftCertificate -> giftCertificateWithTagsDTOS.add(GiftCertificateWithTagsDTO.convertToGiftCertificateWithTagsDTO(giftCertificate)));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown get filtered gift certificates : ", e);
        }
        return giftCertificateWithTagsDTOS;
    }

}
