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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GiftCertificateServiceImplTest {
    private final Long CERTIFICATE_ID1 = 1L;
    private final Long CERTIFICATE_ID2 = 2L;
    private final Long WRONG_CERTIFICATE_ID = 0L;

    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate isNotCreatedCertificate;
    private GiftCertificate certificate2;
    private CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateService giftCertificateService =
            new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository);


    @BeforeEach
    void setUp() {
        certificate1 =
                giftCertificateCreator(1L, "carting", new BigDecimal(40.0), "speed");
        isNotCreatedCertificate =
                giftCertificateCreator(0L, "carting", new BigDecimal(40.0), "speed");
        certificate2 =
                giftCertificateCreator(2L, "dance training", new BigDecimal(30.0), "speed");
        certificateList = Arrays.asList(certificate1, certificate2);
        commonParamsGiftCertificateQuery =
                initCommonParamsQuery(null, null, null, null, 1, 2);
    }

    @Test
    void find_whenGiftCertificatesExisted_thenReturnCertificate() {
        Mockito.when(giftCertificateRepository.findById(CERTIFICATE_ID1)).thenReturn(Optional.ofNullable(certificate1));
        GiftCertificateDTO expected = GiftCertificateDTOConverter.convertToGiftCertificateDTO(certificate1);
        GiftCertificateDTO actual = giftCertificateService.find(CERTIFICATE_ID1);

        Assertions.assertNotNull(actual, "GiftCertificate should not found");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_whenGiftCertificateNotFound_thenCertificateNotFoundException() {

        Mockito.when(giftCertificateRepository.findById(WRONG_CERTIFICATE_ID))
                .thenThrow(GiftCertificateNotFoundException.class);

        Assertions.assertThrows(GiftCertificateNotFoundException.class, ()
                -> giftCertificateService.find(WRONG_CERTIFICATE_ID));
    }

    @Test
    void findAll() {
        List<GiftCertificateDTO> expected = new ArrayList<>();
        Mockito.when(giftCertificateRepository.findAll(commonParamsGiftCertificateQuery))
                .thenReturn(Optional.ofNullable(certificateList));
        certificateList.forEach(giftCertificate -> expected
                .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        List<GiftCertificateDTO> actual = giftCertificateService.findAll(commonParamsGiftCertificateQuery);

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteById_whenGiftCertificateIsNotExist_thenNotFoundException() {

        Assertions.assertThrows(GiftCertificateNotFoundException.class, () ->
                giftCertificateService.deleteById(WRONG_CERTIFICATE_ID));
    }

    @Disabled
    @Test
    void update_whenGiftCertificateUpdated_returnUpdatedGiftCertificate() {
        Optional<GiftCertificate> expectedCertificateFromRepository =
                Optional.ofNullable(changeNameCertificate(certificate1));
        GiftCertificateDTO expectedCertificateFromRepositoryDTO = GiftCertificateDTOConverter
                .convertToGiftCertificateDTO(expectedCertificateFromRepository.get());
        Mockito.when(giftCertificateRepository.findById(eq(expectedCertificateFromRepository.get().getId())))
                .thenReturn(expectedCertificateFromRepository);

        Mockito.when(giftCertificateRepository.update(eq(expectedCertificateFromRepository.get())))
                .thenReturn(expectedCertificateFromRepository);
        GiftCertificateDTO actualGiftCertificateFromService = giftCertificateService
                .update(expectedCertificateFromRepositoryDTO);

        Assertions.assertEquals(expectedCertificateFromRepositoryDTO, actualGiftCertificateFromService);
    }

    @Disabled
    @Test
    void updatePatch_whenGiftCertificateUpdated_returnUpdatedGiftCertificate() {
        Optional<GiftCertificate> expectedUpdateDCertificateFromRepository =
                Optional.ofNullable(changeNameCertificate(certificate1));
        GiftCertificatePatchDTO giftCertificatePatchDTO = new GiftCertificatePatchDTO();
        giftCertificatePatchDTO.setId(CERTIFICATE_ID1);
        giftCertificatePatchDTO.setPrice(new BigDecimal(86));
        Mockito.when(giftCertificateRepository.findById(expectedUpdateDCertificateFromRepository.get().getId()))
                .thenReturn(expectedUpdateDCertificateFromRepository);
        expectedUpdateDCertificateFromRepository.ifPresent(actual -> {
            giftCertificateService.updatePatch(giftCertificatePatchDTO);
            Mockito.verify(giftCertificateRepository).update(actual);
        });
    }

    @Test
    void update_whenGiftCertificateNotExist_returnNotFoundException() {
        certificate1.setId(WRONG_CERTIFICATE_ID);
        Mockito.when(giftCertificateRepository.update(certificate1)).thenThrow(GiftCertificateNotFoundException.class);

        Assertions.assertThrows(GiftCertificateNotFoundException.class, () ->
                giftCertificateService.update(GiftCertificateDTOConverter
                        .convertToGiftCertificateDTO(certificate1)));
    }


    private GiftCertificate changeNameCertificate(GiftCertificate certificate1) {
        certificate1.setName("newName");
        return certificate1;
    }

    private GiftCertificate giftCertificateCreator(Long id, String name, BigDecimal price, String description) {
        return new GiftCertificate(id, name, description, price, LocalDateTime.now().minusDays(2),
                LocalDateTime.now(), Duration.ofDays(30));
    }

    private CommonParamsGiftCertificateQuery initCommonParamsQuery(String name, String tag_name, String sortField,
                                                                   String order, int offset, int limit) {
        CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery = new CommonParamsGiftCertificateQuery();
        commonParamsGiftCertificateQuery.setName(name);
        commonParamsGiftCertificateQuery.setTag_name(tag_name);
        commonParamsGiftCertificateQuery.setOrder(order);
        commonParamsGiftCertificateQuery.setSortField(sortField);
        commonParamsGiftCertificateQuery.setOffset(offset);
        commonParamsGiftCertificateQuery.setLimit(limit);
        return commonParamsGiftCertificateQuery;
    }
}