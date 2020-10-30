package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.DurationConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateWIthTagsMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        DurationConverter durationConverter = new DurationConverter();
        GiftCertificate giftCertificate = new GiftCertificate();
        Tag tag = new Tag();
        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getDouble("price"));
        giftCertificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        giftCertificate.setLastUpdateTime(rs.getTimestamp("last_update_date").toLocalDateTime());
        giftCertificate.setDuration(durationConverter.convertToEntityAttribute(rs.getLong("duration")));
        tag.setId(rs.getLong("tag.id"));
        tag.setName(rs.getString("tag.name"));
        giftCertificate.addTag(tag);
        return giftCertificate;
    }
    }
