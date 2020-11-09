package com.epam.esm.service.impl;

import com.epam.esm.config.ProductSpringConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateDTO;
import com.epam.esm.modelDTO.giftcertificate.GiftCertificateWithTagsDTO;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.NotValidParamsRequest;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateWithTagsDTOConverter;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ContextConfiguration(classes = {ProductSpringConfiguration.class})
@WebAppConfiguration
class GiftCertificateServiceImplTest {
    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private PlatformTransactionManager txManager;

    @Autowired
    @InjectMocks
    private GiftCertificateService giftCertificateService =
            new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository, txManager);

    @BeforeEach
    void setUp() {
        certificate1 = giftCertificateCreator(1L, "carting", 40.0, "speed");
        certificate2 =  giftCertificateCreator(2L, "dance training", 30.0, "speed");
        certificateList = Arrays.asList(certificate1, certificate2);
    }

    @Test
    void find_whenGiftCertificatesExisted_thenReturnCertificate() throws RepositoryException, ServiceException {
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.ofNullable(certificate1));
        GiftCertificateDTO expected = GiftCertificateDTOConverter.convertToGiftCertificateDTO(certificate1);
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
        certificateList.forEach(giftCertificate -> expected
                .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        List<GiftCertificateDTO> actual = giftCertificateService.findAll();

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteById_whenGiftCertificateIsNotExist_thenNotFoundException() throws RepositoryException {
        Mockito.when(giftCertificateRepository.delete(-1L)).thenThrow(GiftCertificateNotFoundException.class);

        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.deleteById(-1L));
    }

    @Test
    void createGiftCertificate_whenCertificateIsCreated_returnGiftCertificate() throws ServiceException, RepositoryException {
        Mockito.when(giftCertificateRepository.create(certificate1)).thenReturn(Optional.ofNullable(certificate1));
        GiftCertificateDTO expected = GiftCertificateDTOConverter.convertToGiftCertificateDTO(certificate1);
        GiftCertificateDTO actual = giftCertificateService.create(certificate1);

        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void getFilteredGiftCertificates_whenGiftCertificatesNotNullAndSortByNameDesc_thenReturnList()
            throws RepositoryException, ServiceException {
        List<GiftCertificate> returnedCertificates = Arrays.asList(certificate2, certificate1);
        Mockito.when(giftCertificateRepository.getSortedGiftCertificates("name", "desc"))
                .thenReturn(returnedCertificates);
        List<GiftCertificateWithTagsDTO> expectedCertificates = new ArrayList<>();
        for (GiftCertificate giftCertificate : returnedCertificates) {
            expectedCertificates.add(GiftCertificateWithTagsDTOConverter.
                    convertToGiftCertificateWithTagsDTO(giftCertificate));
        }
        List<GiftCertificateWithTagsDTO> actualCertificates = giftCertificateService.
                getFilteredGiftCertificates("name", "desc");

        Assertions.assertIterableEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void getFilteredGiftCertificates_whenNotHaveParameters_thenReturnNotFoundException() throws RepositoryException {
        Mockito.when(giftCertificateRepository.getSortedGiftCertificates("", ""))
                .thenThrow(NotValidParamsRequest.class);

        Assertions.assertThrows(NotValidParamsRequest.class, () -> giftCertificateService
                .getFilteredGiftCertificates("", ""));
    }

    @Test
    void findGiftCertificateByPartName_whenThereAreCertificatesWithPartName_thenReturnList()
            throws RepositoryException, ServiceException {
        List<GiftCertificate> certificates = Arrays.asList(certificate1);
        Mockito.when(giftCertificateRepository.findGiftCertificateByPartName("ca")).thenReturn(certificates);
        List<GiftCertificateWithTagsDTO> expectedCertificates = new ArrayList<>();
        certificates.forEach(giftCertificate -> expectedCertificates
                .add(GiftCertificateWithTagsDTOConverter.convertToGiftCertificateWithTagsDTO(giftCertificate)));
        List<GiftCertificateWithTagsDTO> actualCertificates = giftCertificateService.findGiftCertificateByPartName("ca");

        Assertions.assertIterableEquals(expectedCertificates, actualCertificates);
    }

    private GiftCertificate giftCertificateCreator(Long id, String name, Double price, String description){
        return new GiftCertificate(id, name, description, price, LocalDateTime.now().minusDays(2),
                LocalDateTime.now(), Duration.ofDays(30),
                Arrays.asList(new Tag(1L, "spa"), new Tag(2L, "travel")));
    }
}