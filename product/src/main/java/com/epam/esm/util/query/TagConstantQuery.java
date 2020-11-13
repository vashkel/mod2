package com.epam.esm.util.query;

public class TagConstantQuery {

    public static String SQL_DELETE_TAG = "DELETE FROM tag WHERE id=?";
    public static String SQL_FIND_TAG = "SELECT id, name FROM tag WHERE id=?";
    public static String SQL_FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
    public static String SQL_FIND_ALL_TAGS = "SELECT id, name FROM tag";
    public static String SQL_FIND_ALL_TAGS_BY_CERTIFICATE_ID = "SELECT tag.id, tag.name from tag " +
            "left join gift_certificate_tags " +
            "AS gct ON tag.id=gct.tag_id left join gift_certificate AS c ON c.id = gct.gift_Certificate_id where c.id =?";

    public static final String TABLE_NAME = "tag";
    public static final String NAME_COLUMN = "name";
    public static final String KEY = "id";
}
