package com.epam.esm.service.impl;

import com.epam.esm.config.ProductSpringConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ContextConfiguration(classes = {ProductSpringConfiguration.class})
@WebAppConfiguration
class GiftCertificateServiceImplTest {
    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate isNotCreatedCertificate1;
    private GiftCertificate certificate2;
    private CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;

    @Autowired
    @InjectMocks
    private GiftCertificateService giftCertificateService =
            new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository);

    @BeforeEach
    void setUp() {
        certificate1 = giftCertificateCreator(1L, "carting", new BigDecimal(40.0), "speed");
        isNotCreatedCertificate1 = giftCertificateCreator(0L, "carting", new BigDecimal(40.0), "speed");
        certificate2 =  giftCertificateCreator(2L, "dance training", new BigDecimal(30.0), "speed");
        certificateList = Arrays.asList(certificate1, certificate2);
        commonParamsGiftCertificateQuery =
                initCommonParamsQuery(null,null, null, null, 1, 2);
    }

    @Test
    void find_whenGiftCertificatesExisted_thenReturnCertificate()  {
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.ofNullable(certificate1));
        GiftCertificateDTO expected = GiftCertificateDTOConverter.convertToGiftCertificateDTO(certificate1);
        GiftCertificateDTO actual = giftCertificateService.find(1L);

        Assertions.assertNotNull(actual, "GiftCertificate should not found");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_whenGiftCertificateNotFound_thenCertificateNotFoundException(){
        Mockito.when(giftCertificateRepository.findById(0L)).thenThrow(GiftCertificateNotFoundException.class);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.find(0L));
    }

    @Test
    void findAll(){
        List<GiftCertificateDTO> expected = new ArrayList<>();
        Mockito.when(giftCertificateRepository.findAll(commonParamsGiftCertificateQuery)).thenReturn(Optional.ofNullable(certificateList));
        certificateList.forEach(giftCertificate -> expected
                .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        List<GiftCertificateDTO> actual = giftCertificateService.findAll(commonParamsGiftCertificateQuery);

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteById_whenGiftCertificateIsNotExist_thenNotFoundException() {
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.deleteById(-1L));
    }

    @Test
    void update_whenGiftCertificateUpdated_returnUpdatedGiftCertificate(){
        GiftCertificate expectedUpdateDCertificate = changeNameCertificate(certificate1);
        GiftCertificateDTO expected = GiftCertificateDTOConverter
                .convertToGiftCertificateDTOWithoutTag(expectedUpdateDCertificate);
        Mockito.when(giftCertificateRepository.findById(expectedUpdateDCertificate.getId())).thenReturn(Optional.ofNullable(expectedUpdateDCertificate));
//
//       Mockito.when(giftCertificateRepository.update(expectedUpdateDCertificate)).thenReturn(Optional.ofNullable(expectedUpdateDCertificate));
//        Assertions.assertEquals(expected, giftCertificateService
//                .update(GiftCertificateDTOConverter.convertToGiftCertificateDTOWithoutTag(expectedUpdateDCertificate), expectedUpdateDCertificate.getId()));
    }

    private GiftCertificate changeNameCertificate(GiftCertificate certificate1) {
        certificate1.setName("newName");
        return certificate1;
    }

    private GiftCertificate giftCertificateCreator(Long id, String name, BigDecimal price, String description){
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag(1L, "spa"), new Tag(2L, "travel")));
        return new GiftCertificate(id, name, description, price, LocalDateTime.now().minusDays(2),
                LocalDateTime.now(), Duration.ofDays(30), tags);
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