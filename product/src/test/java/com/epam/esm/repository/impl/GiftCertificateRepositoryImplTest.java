package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.config.H2Config;
import com.epam.esm.util.DurationConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Sql({"classpath:drop_schema.sql", "classpath:create_schema.sql"})
@SpringJUnitConfig(H2Config.class)
@WebAppConfiguration
class GiftCertificateRepositoryImplTest {

    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificate certificate1WithTags;
    private GiftCertificate certificate2WithTag1;
    private GiftCertificate certificate2WithTag2;
    private List<GiftCertificate> certificateWithTagsList;


    @Autowired
    GiftCertificateRepository giftCertificateRepository;
    @Autowired
    DurationConverter durationConverter;

    @BeforeEach
    void setUp() {
        certificate1 = new GiftCertificate();
        certificate1.setId(1L);
        certificate1.setName("swimming pool");
        certificate1.setDescription("the best");
        certificate1.setPrice(30.0);
        certificate1.setCreateDate(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        certificate1.setLastUpdateTime(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        certificate1.setDuration(durationConverter.convertToEntityAttribute(2160000000L));

        certificate2 = new GiftCertificate();
        certificate2.setId(2L);
        certificate2.setName("cinema");
        certificate2.setDescription("comedy");
        certificate2.setPrice(15.5);
        certificate2.setCreateDate(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        certificate2.setLastUpdateTime(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        certificate2.setDuration(durationConverter.convertToEntityAttribute(1900800000L));
        certificateList = Arrays.asList(certificate1, certificate2);

        certificate1WithTags = new GiftCertificate();
        certificate1WithTags.setId(1L);
        certificate1WithTags.setName("swimming pool");
        certificate1WithTags.setDescription("the best");
        certificate1WithTags.setPrice(30.0);
        certificate1WithTags.setCreateDate(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        certificate1WithTags.setLastUpdateTime(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        certificate1WithTags.setDuration(durationConverter.convertToEntityAttribute(2160000000L));
        certificate1WithTags.setTags(Arrays.asList(new Tag(1L, "vip")));
    }

    @Test
    void findGiftCertificateById_whenCertificateExist_thenReturnCertificate() throws RepositoryException {
        Assertions.assertEquals(certificate1, giftCertificateRepository.findById(certificate1.getId()));
    }

    @Test
    void findAllGiftCertificates_whenCertificatesExist_thenReturnListOfCertificates() throws RepositoryException {
        Assertions.assertIterableEquals(certificateList, giftCertificateRepository.findAll());
    }

    @Test
    void createGiftCertificate_whenCertificateCreated_returnCertificate() throws RepositoryException {
        Assertions.assertEquals(certificate1, giftCertificateRepository.create(certificate1));
    }

    @Test
    void createGiftCertificate_whenCertificateWithTagsCreated_returnCertificateWithTag() throws RepositoryException {
        Assertions.assertEquals(certificate1WithTags, giftCertificateRepository.create(certificate1WithTags));
    }

    @Test
    void delete_whenCertificateDeleted_thenReturnTrue() throws RepositoryException {
        Assertions.assertTrue(giftCertificateRepository.delete(certificate1.getId()));
    }

    @Test
    void findGiftCertificatesByTagName_whenCertificatesIsFounded_thenReturnListOfCertificates() throws RepositoryException {
        Assertions.assertEquals(2, giftCertificateRepository.findGiftCertificatesByTagName("family").size());
    }

    @Test
    void findGiftCertificateByPartName_whenCertificatesIsFounded_thenReturnListOfCertificates() throws RepositoryException {
        Assertions.assertEquals(1, giftCertificateRepository.findGiftCertificateByPartName("ci").size());
    }

    @Test
    void getSortedGiftCertificates_whenCertificatesSortedByNameOrderASC_thenReturnSortedList() throws RepositoryException {
        Assertions.assertIterableEquals(Arrays.asList(certificate2, certificate1), giftCertificateRepository.getSortedGiftCertificates("name", "asc"));
    }

}