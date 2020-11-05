package com.epam.esm.service.impl;

import com.epam.esm.config.ProductSpringConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.entityDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.NotValidParamsRequest;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ContextConfiguration(classes = {ProductSpringConfiguration.class})
@WebAppConfiguration
class GiftCertificateServiceImplTest {
    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificateDTO giftCertificateDTO;



    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    @InjectMocks
    private GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository);



    @BeforeEach
    public void setUp() {
        giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(1L);
        giftCertificateDTO.setName("massages");
        giftCertificateDTO.setPrice(50.0);
        giftCertificateDTO.setDescription("massage of head");
        giftCertificateDTO.setCreateDate(LocalDateTime.now().minusDays(2));
        giftCertificateDTO.setLastUpdateTime(LocalDateTime.now());
        giftCertificateDTO.setDuration(Duration.ofDays(30));
        certificate1 = new GiftCertificate();
        certificate1.setId(1L);
        certificate1.setName("carting");
        certificate1.setPrice(40.0);
        certificate1.setDescription("speed");
        certificate1.setCreateDate(LocalDateTime.now().minusDays(2));
        certificate1.setLastUpdateTime(LocalDateTime.now());
        certificate1.setDuration(Duration.ofDays(30));
        certificate1.setTags(Arrays.asList(new Tag(1L, "spa"), new Tag(2L, "travel")));
        certificate2 = new GiftCertificate();
        certificate2.setId(2L);
        certificate2.setName("dance training");
        certificate2.setPrice(30.0);
        certificate2.setDescription("speed");
        certificate2.setCreateDate(LocalDateTime.now().minusDays(2));
        certificate2.setLastUpdateTime(LocalDateTime.now());
        certificate2.setDuration(Duration.ofDays(30));
        certificate2.setTags(Arrays.asList(new Tag(1L, "bike"), new Tag(2L, "travel")));
        certificateList = Arrays.asList(certificate1, certificate2);
    }
    @Test
    void find_whenGiftCertificatesExisted_thenReturnCertificate() throws RepositoryException, ServiceException {
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(certificate1);
        GiftCertificateDTO expected = GiftCertificateDTO.convertToGiftCertificateDTO(certificate1);
        GiftCertificateDTO actual = giftCertificateService.find(1L);
        Assertions.assertNotNull(actual, "GiftCertificate should not found");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_whenGiftCertificateNotFound_thenCertificateNotFoundException() throws RepositoryException {
        Mockito.when(giftCertificateRepository.findById(0L)).thenThrow(GiftCertificateNotFoundException.class);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.find(0L));
    }
    @Test
    void findAll() throws RepositoryException, ServiceException {
        List<GiftCertificateDTO> expected = new ArrayList<>();
        Mockito.when(giftCertificateRepository.findAll()).thenReturn(certificateList);
        certificateList.forEach(giftCertificate -> expected.add(GiftCertificateDTO.convertToGiftCertificateDTO(giftCertificate)));
        List<GiftCertificateDTO> actual = giftCertificateService.findAll();
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteById_whenGiftCertificateIsNotExist_thenNotFoundException() throws RepositoryException {
        Mockito.when(giftCertificateRepository.delete(-1L)).thenThrow(GiftCertificateNotFoundException.class);
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.deleteById(-1L));
    }

    @Test
    void createGiftCertificate_whenCeftificateIsCreated_returnGiftCertificate() throws ServiceException, RepositoryException {
        Mockito.when(giftCertificateRepository.create(certificate1)).thenReturn(certificate1);
        GiftCertificateDTO expected = GiftCertificateDTO.convertToGiftCertificateDTO(certificate1);
        GiftCertificateDTO actual = giftCertificateService.create(certificate1);
        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void getFilteredGiftCertificates_whenGiftCertificatesNotNullAndSortByNameDesc_thenReturnList() throws RepositoryException, ServiceException {
        List<GiftCertificate> returnedCertificates = Arrays.asList(certificate2, certificate1);
        Mockito.when(giftCertificateRepository.getFilteredGiftCertificates("name", "desc")).thenReturn(returnedCertificates);
        List<GiftCertificateWithTagsDTO> expectedCertificates = new ArrayList<>();
        for (GiftCertificate giftCertificate : returnedCertificates){
            expectedCertificates.add(GiftCertificateWithTagsDTO.
                    convertToGiftCertificateWithTagsDTO(giftCertificate));
        }
        List<GiftCertificateWithTagsDTO> actualCertificates = giftCertificateService.getFilteredGiftCertificates("name", "desc");
                Assertions.assertIterableEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void getFilteredGiftCertificates_whenNotHaveParameters_thenReturnNotFoundException() throws RepositoryException {
        Mockito.when(giftCertificateRepository.getFilteredGiftCertificates("", "")).thenThrow(NotValidParamsRequest.class);
        Assertions.assertThrows(NotValidParamsRequest.class, () -> giftCertificateService.getFilteredGiftCertificates("", ""));
    }

    @Test
    void findGiftCertificateByPartName_whenThereAreCertificatesWithPartName_thenReturnList() throws RepositoryException, ServiceException {
        List<GiftCertificate> certificates = Arrays.asList(certificate1);
        Mockito.when(giftCertificateRepository.findGiftCertificateByPartName("ca")).thenReturn(certificates);
        List<GiftCertificateWithTagsDTO> expectedCertificates = new ArrayList<>();
                certificates.forEach(giftCertificate -> expectedCertificates.add(GiftCertificateWithTagsDTO.convertToGiftCertificateWithTagsDTO(giftCertificate)));
        List<GiftCertificateWithTagsDTO> actualCertificates = giftCertificateService.findGiftCertificateByPartName("ca");
        Assertions.assertIterableEquals(expectedCertificates, actualCertificates);
}
}