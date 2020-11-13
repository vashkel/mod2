create schema if not exists test_gift_certificate;

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
insert into tag (name) values ('vip');
insert into tag (name) values ('family');

insert into gift_certificate (name, description, price, create_date, last_update_date, duration) values ('swimming pool', 'the best', '30', '2020-10-10 10:10:10', '2020-10-10 10:10:10', '2160000000');
insert into gift_certificate (name, description, price, create_date, last_update_date, duration) values ('cinema', 'comedy', '15.5', '2020-10-10 10:10:10', '2020-10-10 10:10:10', '1900800000');

insert into gift_certificate_tags (gift_certificate_id, tag_id) values (1, 2);
insert into gift_certificate_tags (gift_certificate_id, tag_id) values (2, 1);
insert into gift_certificate_tags (gift_certificate_id, tag_id) values (2, 2);
