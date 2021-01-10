create schema if not exists giftcertificate;

create table if not exists gift_certificate
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

create table if not exists tag
(
    id bigint auto_increment
        primary key,
    name varchar(45) not null,
    constraint tag_name_uindex
        unique (name)
);

create table if not exists gift_certificate_tags
(
    id bigint auto_increment
        primary key,
    gift_certificate_id bigint not null,
    tag_id bigint not null,
    constraint `gift-certificate_tags_gift_certificate_id_fk`
        foreign key (gift_certificate_id) references gift_certificate (id),
    constraint `gift-certificate_tags_tag_test_id_fk`
        foreign key (tag_id) references tag (id)
);

create table user
(
    id bigint auto_increment
        primary key,
    name varchar(45) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    role varchar(20) default 'GUEST' not null,
    status varchar(20) default 'ACTIVE' not null,
    constraint user_email_uindex
        unique (email)
);



create table if not exists users_order
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

create table if not exists users_order_gift_certificate
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

