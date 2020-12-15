package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificatePatchDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificatePatchDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String NOT_FOUND = "locale.message.GiftCertificateNotFound";
    private static final String CERTIFICATE_EXIST = "locale.message.CertificateExist";

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;

    }

    @Transactional(readOnly = true)
    @Override
    public GiftCertificateDTO find(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
        if (giftCertificate.isPresent()) {
            return GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate.get());
        } else {
            throw new GiftCertificateNotFoundException(NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<GiftCertificateDTO> findAll(CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery) {
        List<GiftCertificateDTO> giftCertificateDTOS = new ArrayList<>();
        Optional<List<GiftCertificate>> certificates = giftCertificateRepository.findAll(commonParamsGiftCertificateQuery);
        certificates.ifPresent(giftCertificates -> giftCertificates.forEach(giftCertificate -> giftCertificateDTOS
                .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate))));
        return giftCertificateDTOS;
    }

    @Override
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        Optional<List<GiftCertificate>> certificatesFromDb;
        GiftCertificate giftCertificate;
        Set<Tag> tags = new HashSet<>();
        certificatesFromDb = giftCertificateRepository.findByName(giftCertificateDTO.getName());
        if (!certificatesFromDb.isPresent()) {
            throw new GiftCertificateNotFoundException(CERTIFICATE_EXIST);
        }
        giftCertificateDTO.setCreateDate(LocalDateTime.now());
        giftCertificateDTO.setLastUpdateTime(LocalDateTime.now());
        giftCertificateDTO.setDuration(Duration.ofDays(30));
        giftCertificate = GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO);

        for (Tag insertedTag : giftCertificate.getTags()) {
            Optional<Tag> tagFromDb = tagRepository.findByName(insertedTag.getName());
            if (tagFromDb.isPresent()) {
                tags.add(tagFromDb.get());
            } else {
                tags.add(insertedTag);
            }
        }
        giftCertificate.setTags(tags);
        Optional<GiftCertificate> createdGiftCertificate = giftCertificateRepository.create(giftCertificate);
        if (createdGiftCertificate.isPresent())
            giftCertificateDTO = GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate);
        return giftCertificateDTO;
    }

    @Override
    public void deleteById(Long id) {
        Optional<GiftCertificate> createdCertificate = giftCertificateRepository.findById(id);
        if (!createdCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(NOT_FOUND);
        }
        giftCertificateRepository.deleteFromUsersOrdersGiftCertificateTable(createdCertificate.get().getId());
        giftCertificateRepository.delete(createdCertificate.get());
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
        GiftCertificateDTO updatedGiftCertificateDTO = null;
        GiftCertificate giftCertificate;
        Optional<GiftCertificate> certificate = giftCertificateRepository.findById(certificateDTO.getId());
        if (!certificate.isPresent()) {
            throw new GiftCertificateNotFoundException(NOT_FOUND);
        }
        giftCertificate = GiftCertificateDTOConverter.convertFromGiftCertificateDTO(certificateDTO);
        giftCertificate.setCreateDate(certificate.get().getCreateDate());
        giftCertificate.setLastUpdateTime(LocalDateTime.now());
        giftCertificate.setTags(certificate.get().getTags());
        Optional<GiftCertificate> updatedCertificate = giftCertificateRepository.update(giftCertificate);
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificateDTO = GiftCertificateDTOConverter
                    .convertToGiftCertificateDTO(updatedCertificate.get());
        }
        return updatedGiftCertificateDTO;
    }

    @Override
    public GiftCertificateDTO updatePatch(GiftCertificatePatchDTO giftCertificatePatchDTO) {
        GiftCertificate newGiftCertificate;
        Optional<GiftCertificate> updatedCertificate = Optional.empty();
        Optional<GiftCertificate> oldGiftCertificate = giftCertificateRepository.findById(giftCertificatePatchDTO.getId());
        if (!oldGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(NOT_FOUND);
        }
        newGiftCertificate = GiftCertificatePatchDTOConverter.convertFromGiftCertificatePatchDTO(giftCertificatePatchDTO);
        newGiftCertificate.setLastUpdateTime(LocalDateTime.now());
        newGiftCertificate.setTags(oldGiftCertificate.get().getTags());
        if (hasUpdateValues(newGiftCertificate)) {
            setUpdateValues(oldGiftCertificate.get(), newGiftCertificate);
            updatedCertificate = giftCertificateRepository.update(oldGiftCertificate.get());
        }
        return GiftCertificateDTOConverter.convertToGiftCertificateDTO(updatedCertificate.get());
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
}
