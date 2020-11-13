create schema giftcertificate collate utf8mb4_0900_ai_ci;

create table gift_certificate
(
	id bigint auto_increment
		primary key,
	name varchar(45) not null,
	description int null,
	price int not null,
	create_date datetime not null,
	last_update_date datetime not null,
	duration long not null
);

create table tag
(
	name varchar(45) not null,
	id bigint auto_increment
		primary key,
	constraint tag_name_uindex
		unique (name)
);

create table `gift-certificate_tags`
(
	id bigint auto_increment
		primary key,
	gift_certificate_id bigint not null,
	tag_id bigint not null,
	constraint `gift-certificate_tags_gift_certificate_id_fk`
		foreign key (gift_certificate_id) references gift_certificate (id),
	constraint `gift-certificate_tags_tagg_id_fk`
		foreign key (tag_id) references tag (id)
);

