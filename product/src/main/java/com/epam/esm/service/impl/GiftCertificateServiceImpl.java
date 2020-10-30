package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.RepositoryException;
import exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {


    private GiftCertificateRepositoryImpl giftCertificateRepository;
    private TagRepositoryImpl tagRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepositoryImpl giftCertificateRepository, TagRepositoryImpl tagRepository, ObjectMapper objectMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public GiftCertificateDTO find(Long id) throws ServiceException {
        try {
            return GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificateRepository.findGiftCertificateById(id));
        }catch (RepositoryException e){
            throw new ServiceException("An exception was thrown during find gift certificate : ", e);
        }

    }

    @Override
    public List<GiftCertificateDTO> findAll() throws  ServiceException {
        List<GiftCertificateDTO> giftCertificateDTOList = new ArrayList<>();
            try {
                for (GiftCertificate giftCertificate : giftCertificateRepository.findAllGiftCertificates()){
                    giftCertificateDTOList.add(GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificate));
                }
            }catch (RepositoryException e){
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
            for (Tag insertedTag : giftCertificate.getTags()) {
                long tagID = tagRepository.create(insertedTag);
                insertedTag.setId(tagID);
            }
            return GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificateRepository.createGiftCertificate(giftCertificate));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown create gift certificate : ", e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
       try {
           giftCertificateRepository.delete(id);
       } catch (RepositoryException e) {
           throw new ServiceException("An exception was thrown delete gift certificate : ", e);
       }
    }

    @Override
    public long update(GiftCertificate giftCertificate) throws ServiceException {
        giftCertificate.setLastUpdateTime(LocalDateTime.now());
        try {
            return giftCertificateRepository.update(giftCertificate);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown update gift certificate : ", e);
        }

    }

    @Override
    public List<GiftCertificate> findCertificatesByTagName(String tagName) throws ServiceException {
        try {
            return giftCertificateRepository.findGiftCertificatesByTagName(tagName);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown find gift certificate by name of tag : ", e);
        }
    }

    @Override
    public List<GiftCertificate> findGiftCertificateByPartName(String partName) {
        return giftCertificateRepository.findGiftCertificateByPartName(partName);
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesSortedByName(String order) {
        return giftCertificateRepository.findGiftCertificatesSortedByName(order);
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesSortedByNameDESC() {
        return giftCertificateRepository.findGiftCertificatesSortedByNameDESC();
    }

}
