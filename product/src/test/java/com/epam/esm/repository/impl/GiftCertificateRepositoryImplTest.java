package com.epam.esm.repository.impl;

import com.epam.esm.config.ProductSpringConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.config.H2Config;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.util.DurationConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProductSpringConfiguration.class},
loader = AnnotationConfigContextLoader.class)
@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:schema.sql")
@Sql({"classpath:schema.sql"})
//@Transactional
//@EnableAutoConfiguration
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GiftCertificateRepositoryImplTest {

    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificate certificate1WithTags;
    private CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery;

    @Resource
    private GiftCertificateRepository giftCertificateRepository;
    @Resource
    private DurationConverter durationConverter;


    @BeforeEach
    void setUp() {
        certificate1 = giftCertificateCreator(1L, "swimming pool", new BigDecimal(35.5),
                "the best", durationConverter.convertToEntityAttribute(2160000000L));
        certificate1.setTags(new HashSet<>(Arrays.asList(new Tag(2L, "family"))));

        certificate2 = giftCertificateCreator(2L, "cinema", new BigDecimal(15.5),
                "comedy", durationConverter.convertToEntityAttribute(1900800000L));
        certificate2.setTags(new HashSet<>(Arrays.asList(new Tag(1L, "vip"), new Tag(2L, "family"))));

        certificateList = Arrays.asList(certificate1, certificate2);

        commonParamsGiftCertificateQuery =
                initCommonParamsQuery(null, null, null, null, 1, 10);
    }

    @Test
    void findById_whenCertificateExist_thenReturnCertificate() {
        certificate1.setTags(new HashSet<>(Arrays.asList(new Tag(2L, "family"))));
        Long certificateId = certificate1.getId();
        Optional<GiftCertificate> byId = giftCertificateRepository.findById(certificateId);
        Assertions.assertEquals(Optional.of(certificate1), byId);
    }

    @Test
    void updateCertificate_whenCertificateUpdated_thenReturnTrue() {
        certificate2.setPrice(new BigDecimal(40.0));
        Assertions.assertEquals(certificate2.getPrice(), giftCertificateRepository.update(certificate2).get().getPrice());
    }

    @Test
    void findAllGiftCertificates_whenCertificatesExist_thenReturnListOfCertificates() {
        Optional<List<GiftCertificate>> certificateList = Optional.ofNullable(this.certificateList);
        Assertions.assertIterableEquals(certificateList.get(), giftCertificateRepository
                .findAll(commonParamsGiftCertificateQuery).get());
    }

    @Test
    void createGiftCertificate_whenCertificateCreated_returnCertificate() {

        Assertions.assertEquals(Optional.of(certificate1), giftCertificateRepository.create(certificate1));
    }

    @Test
    void createGiftCertificate_whenCertificateWithTagsCreated_returnCertificateWithTag() {

        Assertions.assertEquals(Optional.of(certificate1WithTags), giftCertificateRepository
                .create(certificate1WithTags));
    }


    private GiftCertificate giftCertificateCreator(Long id, String name, BigDecimal price,
                                                   String description, Duration duration) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setCreateDate(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        giftCertificate.setLastUpdateTime(Timestamp.valueOf("2020-10-10 10:10:10").toLocalDateTime());
        giftCertificate.setDuration(duration);
        return giftCertificate;
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