DROP TABLE IF EXISTS gift_certificate_tags;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS userorder_gift_certificate;
DROP TABLE IF EXISTS gift_certificate;
DROP SCHEMA IF EXISTS test_gift_certificate;

create schema if not exists giftcertificate;

create table gift_certificate
(
    id bigint auto_increment
        primary key,
    name varchar(45) not null,
    description varchar(45) null,
    price double not null,
    create_date timestamp not null,
    last_update_date timestamp not null,
    duration mediumtext not null
);

create table revinfo
(
    REVTSTMP bigint not null,
    REV int auto_increment
        primary key
);

create table gift_certificate_aud
(
    id bigint not null,
    name varchar(45) not null,
    description varchar(45) null,
    price double not null,
    create_date timestamp not null,
    last_update_date timestamp not null,
    duration mediumtext not null,
    AUDIT_REVISION int not null,
    ACTION_TYPE int null,
    AUDIT_REVISION_END int null,
    AUDIT_REVISION_END_TS timestamp null,
    primary key (id, AUDIT_REVISION),
    constraint gift_certificate_aud_revinfo_REV_fk
        foreign key (AUDIT_REVISION) references revinfo (REV)
);

create table tag
(
    id bigint auto_increment
        primary key,
    name varchar(45) not null,
    constraint tag_name_uindex
        unique (name)
);

create table gift_certificate_tags
(
    id bigint auto_increment
        primary key,
    gift_certificate_id bigint not null,
    tag_id bigint not null,
    constraint `gift-certificate_tags_gift_certificate_id_fk`
        foreign key (gift_certificate_id) references gift_certificate (id),
    constraint gift_certificate_tags_tag_id_fk
        foreign key (tag_id) references tag (id)
);

create table tag_aud
(
    id bigint not null,
    name varchar(45) null,
    AUDIT_REVISION int not null,
    ACTION_TYPE int null,
    AUDIT_REVISION_END int null,
    AUDIT_REVISION_END_TS timestamp null,
    primary key (id, AUDIT_REVISION),
    constraint tag_aud_revinfo_REV_fk
        foreign key (AUDIT_REVISION) references revinfo (REV)
);

create table user
(
    id bigint auto_increment
        primary key,
    name varchar(45) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    role varchar(20) default 'USER' not null,
    status varchar(20) default 'ACTIVE' not null,
    constraint user_email_uindex
        unique (email)
);

create table users_order
(
    id bigint auto_increment
        primary key,
    create_date timestamp not null,
    cost double not null,
    user_id bigint null,
    constraint order_user_id_fk
        foreign key (user_id) references user (id)
);

create index users_order_cost_index
    on users_order (cost);

create index users_order_user_id_index
    on users_order (user_id);

create index users_order_user_id_index_2
    on users_order (user_id);

create table users_order_gift_certificate
(
    id bigint auto_increment
        primary key,
    order_id bigint null,
    gift_certificate_id bigint null,
    constraint users_order_gift_certificate_gift_certificate_id_fk
        foreign key (gift_certificate_id) references gift_certificate (id),
    constraint users_order_gift_certificate_users_order_id_fk
        foreign key (order_id) references users_order (id)
);

