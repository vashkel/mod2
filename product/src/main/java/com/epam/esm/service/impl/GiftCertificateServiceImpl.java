package com.epam.esm.service.impl;

import com.epam.esm.controller.GiftCertificatesController;
import com.epam.esm.controller.TagController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.SelectFilterCreator;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateWithTagsDTOConverter;
import com.epam.esm.util.ParamName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public GiftCertificateWithTagsDTO find(Long id){
            Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
            if (!giftCertificate.isPresent()) {
                throw new GiftCertificateNotFoundException("certificate not found");
            }
        return GiftCertificateWithTagsDTOConverter.convertToGiftCertificateWithTagsDTO(giftCertificateRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GiftCertificateWithTagsDTO> findAll(Map<String, String> filterParam){
        List<GiftCertificateWithTagsDTO> giftCertificateWithTagsDTOS = new ArrayList<>();
        deleteWrongSearchParam(filterParam);
        deleteWrongSortParam(filterParam);

        int limit = Integer.parseInt(filterParam.get("limit"));
        int offset = Integer.parseInt(filterParam.get("offset"));
        Pagination pagination = new Pagination(limit, offset);

        SelectFilterCreator query = new SelectFilterCreator();
        String hql = query.createFilterQuery(filterParam);
        List<GiftCertificate> certificates = giftCertificateRepository.findAll(hql, pagination);
        if (!certificates.isEmpty()) {
            giftCertificateWithTagsDTOS.addAll(GiftCertificateWithTagsDTOConverter.convertListToGiftCertificatesWithTagsDTO(certificates));
        }
        return giftCertificateWithTagsDTOS;
    }

    @Override
    public GiftCertificateWithTagsDTO create(GiftCertificateWithTagsDTO giftCertificateWithTagsDTO) {
        Optional<List<GiftCertificate>> createdCertificates = giftCertificateRepository.findByName(giftCertificateWithTagsDTO.getName());
        GiftCertificate giftCertificate;
        if (!createdCertificates.isPresent()) {
            throw new EntityExistsException("certificate have already existed");
        }
        giftCertificateWithTagsDTO.setCreateDate(LocalDateTime.now());
        giftCertificateWithTagsDTO.setLastUpdateTime(LocalDateTime.now());
        giftCertificateWithTagsDTO.setDuration(Duration.ofDays(30));
        giftCertificate = GiftCertificateWithTagsDTOConverter.convertToGiftCertificate(giftCertificateWithTagsDTO);
        Optional<GiftCertificate> createdGiftCertificate = giftCertificateRepository.create(giftCertificate);
        return GiftCertificateWithTagsDTOConverter.convertToGiftCertificateWithTagsDTO(createdGiftCertificate.get());
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
    public GiftCertificateDTO update(GiftCertificate giftCertificate, Long id) {
        GiftCertificateDTO giftCertificateDTO = null;
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
        return giftCertificateDTO;
    }

    @Override
    public List<GiftCertificateWithTagsDTO> findCertificatesByTagName(String tagName) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificatesByTagName(tagName);
        return checkCertificateListAndFillWithTags(giftCertificates);
    }

    private List<GiftCertificateWithTagsDTO> checkCertificateListAndFillWithTags(List<GiftCertificate> giftCertificates) {
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
    public List<GiftCertificateWithTagsDTO> getFilteredListCertificates(Map<String, String> filterParam) {
        deleteWrongSearchParam(filterParam);
        deleteWrongSortParam(filterParam);
        return checkCertificateListAndFillWithTags(giftCertificateRepository.filterCertificate(filterParam));
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
