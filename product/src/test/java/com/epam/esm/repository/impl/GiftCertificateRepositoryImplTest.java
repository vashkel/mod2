package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.config.H2Config;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.util.DurationConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Sql({"classpath:drop_schema.sql", "classpath:create_schema.sql"})
@SpringJUnitConfig(H2Config.class)
@WebAppConfiguration
@Transactional
class GiftCertificateRepositoryImplTest {

    private List<GiftCertificate> certificateList;
    private GiftCertificate certificate1;
    private GiftCertificate certificate2;
    private GiftCertificate certificate1WithTags;
    private CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private DurationConverter durationConverter;

    @BeforeEach
    void setUp() {
        certificate1 = giftCertificateCreator(1L, "swimming pool", new BigDecimal(30.0), "the best",
                durationConverter.convertToEntityAttribute(2160000000L));
        certificate1.setTags(new HashSet<>(Arrays.asList(new Tag(1L, "family"))));

        certificate2 = giftCertificateCreator(2L, "cinema", new BigDecimal(15.5), "comedy",
                durationConverter.convertToEntityAttribute(1900800000L));
        certificate2.setTags(new HashSet<>(Arrays.asList(new Tag(1L, "family"), new Tag(2L, "vip"))));

        certificateList = Arrays.asList(certificate1, certificate2);

        commonParamsGiftCertificateQuery =
                initCommonParamsQuery(null, null,null, null, 1, 10);
    }

    @Test
    void findById_whenCertificateExist_thenReturnCertificate()  {

        Assertions.assertEquals(Optional.of(certificate1), giftCertificateRepository.findById(certificate1.getId()));
    }

    @Test
    void updateCertificate_whenCertificateUpdated_thenReturnTrue(){
        certificate2.setPrice(new BigDecimal(40.0));

        Assertions.assertEquals(certificate2.getPrice(), giftCertificateRepository.update(certificate2).get().getPrice());
    }

    @Test
    void findAllGiftCertificates_whenCertificatesExist_thenReturnListOfCertificates() {
        Optional<List<GiftCertificate>> certificateList = Optional.ofNullable(this.certificateList);
        Assertions.assertIterableEquals(certificateList.get(), giftCertificateRepository.findAll(commonParamsGiftCertificateQuery).get());
    }

    @Test
    void createGiftCertificate_whenCertificateCreated_returnCertificate() {

        Assertions.assertEquals(Optional.of(certificate1), giftCertificateRepository.create(certificate1));
    }

    @Test
    void createGiftCertificate_whenCertificateWithTagsCreated_returnCertificateWithTag()  {

        Assertions.assertEquals(Optional.of(certificate1WithTags), giftCertificateRepository.create(certificate1WithTags));
    }

//    @Test
//    void delete_whenCertificateDeleted_thenReturnTrue() {
//      Assertions.assertTrue(giftCertificateRepository.delete(certificate1.getId());
//    }

    private GiftCertificate giftCertificateCreator(Long id, String name, BigDecimal price, String description, Duration duration){
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