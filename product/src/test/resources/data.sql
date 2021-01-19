insert into tag (name) values ('vip');
insert into tag (name) values ('family');

insert into gift_certificate (name, description, price, create_date, last_update_date, duration) values ('swimming pool', 'the best', '35.5', '2020-10-10 10:10:10', '2020-10-10 10:10:10', '2160000000');
insert into gift_certificate (name, description, price, create_date, last_update_date, duration) values ('cinema', 'comedy', '15.5', '2020-10-10 10:10:10', '2020-10-10 10:10:10', '1900800000');

insert into gift_certificate_tags (gift_certificate_id, tag_id) values (1, 2);
insert into gift_certificate_tags (gift_certificate_id, tag_id) values (2, 1);
insert into gift_certificate_tags (gift_certificate_id, tag_id) values (2, 2);