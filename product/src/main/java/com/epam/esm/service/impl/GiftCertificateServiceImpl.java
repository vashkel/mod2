package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.pagination.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.SelectFilterCreator;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.ParamName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
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
    public GiftCertificateDTO find(Long id){
            Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
            if (!giftCertificate.isPresent()) {
                throw new GiftCertificateNotFoundException("certificate not found");
            }
        return GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificateRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GiftCertificateDTO> findAll(Map<String, String> filterParam) {
        List<GiftCertificateDTO> giftCertificateDTOS = new ArrayList<>();
        Pagination pagination = null;
        String hql;
        deleteWrongSearchParam(filterParam);
        deleteWrongSortParam(filterParam);
        try {
            int limit = Integer.parseInt(filterParam.get("limit"));
            int offset = Integer.parseInt(filterParam.get("offset"));
            pagination = new Pagination(limit, offset);
        }catch (NumberFormatException e){
            throw new PaginationException("please input limit and offset");
        }
        hql = SelectFilterCreator.createFilterQuery(filterParam);

        List<GiftCertificate> certificates = giftCertificateRepository.findAll(hql, pagination);
        if (!certificates.isEmpty()) {
            certificates.forEach(giftCertificate -> giftCertificateDTOS
                    .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        }
        return giftCertificateDTOS;
    }

    @Override
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        Optional<List<GiftCertificate>> certificatesFromDb;
        GiftCertificate giftCertificate;
        Set<Tag> tags = new HashSet<>();

        certificatesFromDb = giftCertificateRepository.findByName(giftCertificateDTO.getName());
        if (!certificatesFromDb.isPresent()) {
            throw new EntityExistsException("certificate have already existed");
        }
        giftCertificateDTO.setCreateDate(LocalDateTime.now());
        giftCertificateDTO.setLastUpdateTime(LocalDateTime.now());
        giftCertificateDTO.setDuration(Duration.ofDays(30));
        giftCertificate = GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO);

        for (Tag insertedTag : giftCertificate.getTags()) {
            Optional<Tag> tagFromDb = tagRepository.findByName(insertedTag.getName());
            if(tagFromDb.isPresent()){
                tags.add(tagFromDb.get());
            }else {
                tags.add(insertedTag);
            }
            }
        giftCertificate.setTags(tags);
        Optional<GiftCertificate> createdGiftCertificate = giftCertificateRepository.create(giftCertificate);
        return GiftCertificateDTOConverter.convertToGiftCertificateDTO(createdGiftCertificate.get());
    }

    @Override
    public void deleteById(Long id) {
        Optional<GiftCertificate> createdCertificate = giftCertificateRepository.findById(id);
        if (!createdCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException("gift certificate not found");
        }
        giftCertificateRepository.delete(createdCertificate.get());
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO, Long id) {
        GiftCertificateDTO giftCertificateDTO = null;
        GiftCertificate giftCertificate = null;
        Optional<GiftCertificate> certificate = giftCertificateRepository.findById(id);
        if (!certificate.isPresent()) {
            throw new GiftCertificateNotFoundException("gift certification not found");
        }
        certificateDTO.setId(id);
        certificateDTO.setLastUpdateTime(LocalDateTime.now());
        giftCertificate = GiftCertificateDTOConverter.convertFromGiftCertificateDTO(certificateDTO);
        if (giftCertificateRepository.update(giftCertificate)) {
            giftCertificateDTO = GiftCertificateDTOConverter
                    .convertToGiftCertificateDTO(giftCertificateRepository.findById(id).get());
        }
        return giftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> findCertificatesByTagName(String tagName) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificatesByTagName(tagName);
        return checkCertificateListAndFillWithTags(giftCertificates);
    }

    private List<GiftCertificateDTO> checkCertificateListAndFillWithTags(List<GiftCertificate> giftCertificates) {
        List<GiftCertificateDTO> giftCertificateDTOS = new ArrayList<>();
        if (giftCertificates.isEmpty()) {
            throw new GiftCertificateNotFoundException("gift Certificate not found");
        }
        for (com.epam.esm.entity.GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = tagRepository.findAllTagsByCertificateId(giftCertificate.getId());
            for (Tag tag : tags) {
                giftCertificate.getTags().add(tag);
            }
            giftCertificateDTOS.add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate));
        }
        return giftCertificateDTOS;
    }

    private void deleteWrongSortParam(Map<String, String> filterParam) {
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleDirectionParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.ORDER.getParamName())) {
                        return entry.getValue().equals(e);
                    }
                    return true;
                })
        );
        filterParam.entrySet().removeIf(
                entry -> ParamName.getPossibleFieldParam().stream().noneMatch(e -> {
                    if (entry.getKey().equals(ParamName.SORT_FIELD.getParamName())) {
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
