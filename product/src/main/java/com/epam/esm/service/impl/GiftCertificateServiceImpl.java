package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.modelDTO.GiftCertificateDTO;
import com.epam.esm.modelDTO.GiftCertificatePatchDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificatePatchDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

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
    public List<GiftCertificateDTO> findAll(CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery) {
        List<GiftCertificateDTO> giftCertificateDTOS = new ArrayList<>();
        Optional<List<GiftCertificate>> certificates = giftCertificateRepository.findAll(commonParamsGiftCertificateQuery);
        if (!certificates.get().isEmpty()) {
            certificates.get().forEach(giftCertificate -> giftCertificateDTOS
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
        GiftCertificate giftCertificate;
        Optional<GiftCertificate> certificate = giftCertificateRepository.findById(id);
        if (!certificate.isPresent()) {
            throw new GiftCertificateNotFoundException("gift certification not found");
        }
        certificateDTO.setId(id);
        certificateDTO.setLastUpdateTime(LocalDateTime.now());
        giftCertificate = GiftCertificateDTOConverter.convertFromGiftCertificateDTO(certificateDTO);
        if (giftCertificateRepository.update(giftCertificate).isPresent()) {
            giftCertificateDTO = GiftCertificateDTOConverter
                    .convertToGiftCertificateDTO(giftCertificateRepository.findById(id).get());
        }
        return giftCertificateDTO;
    }

    @Override
    public GiftCertificatePatchDTO updatePatch(GiftCertificatePatchDTO giftCertificatePatchDTO, Long id) {
        GiftCertificate newGiftCertificate;
        Optional<GiftCertificate> oldGiftCertificate = giftCertificateRepository.findById(id);
        if (!oldGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException("gift certification not found");
        }
        giftCertificatePatchDTO.setId(oldGiftCertificate.get().getId());
        giftCertificatePatchDTO.setLastUpdateTime(LocalDateTime.now());
        newGiftCertificate = GiftCertificatePatchDTOConverter.convertFromGiftCertificatePatchDTO(giftCertificatePatchDTO);
        if (hasUpdateValues(newGiftCertificate)) {
            setUpdateValues(oldGiftCertificate.get(), newGiftCertificate);
            giftCertificateRepository.update(oldGiftCertificate.get());
        }
        giftCertificatePatchDTO = GiftCertificatePatchDTOConverter
                .convertToGiftCertificateDTO(oldGiftCertificate.get());
        return giftCertificatePatchDTO;
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

    private boolean hasUpdateValues(GiftCertificate giftCertificate) {
        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        BigDecimal price = giftCertificate.getPrice();
        Duration duration = giftCertificate.getDuration();
        return Stream.of(name, description, price, duration).anyMatch(Objects::nonNull);
    }

    private void setUpdateValues(GiftCertificate oldGiftCertificate, GiftCertificate newGiftCertificate) {
        Optional.ofNullable(newGiftCertificate.getName()).ifPresent(oldGiftCertificate::setName);
        Optional.ofNullable(newGiftCertificate.getDescription()).ifPresent(oldGiftCertificate::setDescription);
        Optional.ofNullable(newGiftCertificate.getPrice()).ifPresent(oldGiftCertificate::setPrice);
        Optional.ofNullable(newGiftCertificate.getDuration()).ifPresent(oldGiftCertificate::setDuration);
        oldGiftCertificate.setLastUpdateTime(LocalDateTime.now());
    }

//    private void deleteWrongSortParam(Map<String, String> filterParam) {
//        filterParam.entrySet().removeIf(
//                entry -> ParamName.getPossibleDirectionParam().stream().noneMatch(e -> {
//                    if (entry.getKey().equals(ParamName.ORDER.getParamName())) {
//                        return entry.getValue().equals(e);
//                    }
//                    return true;
//                })
//        );
//        filterParam.entrySet().removeIf(
//                entry -> ParamName.getPossibleFieldParam().stream().noneMatch(e -> {
//                    if (entry.getKey().equals(ParamName.SORT_FIELD.getParamName())) {
//                        return entry.getValue().equals(e);
//                    }
//                    return true;
//                })
//        );
//    }
//
//    private void deleteWrongSearchParam(Map<String, String> filterParam) {
//        filterParam.entrySet().removeIf(entry -> Arrays.stream(ParamName.values())
//                .noneMatch(paramName -> paramName.getParamName().equals(entry.getKey())));
//    }

}
