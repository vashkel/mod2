package com.epam.esm.util.query;

public class CertificateConstantQuery {

    public static String SQL_FIND_CERTIFICATES_BY_TAG = "SELECT DISTINCT  c.id, c.name, c.description, c.price, " +
            "c.create_date, c.last_update_date, c.duration FROM gift_certificate AS c LEFT JOIN  gift_certificate_tags " +
            "AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id WHERE tag.name=?";
    public static String SQL_FIND_CERTIFICATES_BY_PART_NAME = "SELECT DISTINCT  c.id, c.name, c.description, c.price, " +
            "c.create_date, c.last_update_date, c.duration FROM gift_certificate AS c LEFT JOIN  gift_certificate_tags" +
            " AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id WHERE c.name LIKE ?";
    public static String SQL_FIND_GIFT_CERTIFICATE_BY_NAME = "SELECT DISTINCT  c.id, c.name, c.description, c.price," +
            "c.create_date, c.last_update_date, c.duration FROM gift_certificate AS c LEFT JOIN  gift_certificate_tags" +
            "AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id WHERE c.name = ?";
    public static String SQL_FIND_ALL_GIFT_CERTIFICATES = "SELECT DISTINCT  c.id, c.name, c.description," +
            "c.price, c.create_date, c.last_update_date, c.duration FROM gift_certificate AS c LEFT JOIN " +
            "gift_certificate_tags AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id";
    public static String SQL_SAVE_TAG_ID_AND_GIFT_CERTIFICATE_ID =
            "INSERT INTO gift_certificate_tags(gift_certificate_id,tag_id) VALUES (?,?)";
    public static String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    public static String SQL_DELETE_DEPENDED_TAG = "DELETE FROM gift_certificate_tags WHERE gift_certificate_id =?";
    public static String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name =?, description=?, price=?, " +
            "last_update_date=?, duration=? WHERE id=?";
    public static String SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS = "SELECT DISTINCT  c.id, c.name, c.description, " +
            "c.price, c.create_date, c.last_update_date, c.duration FROM gift_certificate AS c LEFT JOIN " +
            "gift_certificate_tags AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id ";

    public static final String TABLE_NAME = "gift_certificate";
    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PRICE_COLUMN = "price";
    public static final String CREATE_DATE_COLUMN = "create_date";
    public static final String LAST_UPDATE_COLUMN = "last_update_date";
    public static final String DURATION_COLUMN = "duration";
    public static final String KEY = "id";
}
