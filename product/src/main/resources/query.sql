
select tag.id, tag.name, user_id, uo.cost from tag LEFT JOIN gift_certificate_tags gct on tag.id = gct.tag_id
                                                   LEFT JOIN gift_certificate gc on gct.gift_certificate_id = gc.id
                                                   LEFT JOIN users_order_gift_certificate uogc on gc.id = uogc.gift_certificate_id
                                                   LEFT JOIN users_order uo on uogc.order_id = uo.id
where user_id = (select user_id from users_order
                 where users_order.cost = ( select max(cost) from giftcertificate.users_order) group by user_id);

#
#
explain  select tag.id , COUNT(tag.id) as 'tagID', tag.name, user_id, uo.cost from tag
                                                                                       LEFT JOIN gift_certificate_tags gct on tag.id = gct.tag_id
                                                                                       LEFT JOIN gift_certificate gc on gct.gift_certificate_id = gc.id
                                                                                       LEFT JOIN users_order_gift_certificate uogc on gc.id = uogc.gift_certificate_id
                                                                                       LEFT JOIN users_order uo on uogc.order_id = uo.id
         where user_id = (select user_id from users_order
                          where users_order.cost = ( select max(cost) from giftcertificate.users_order))
         group by tag.id order by tagID DESC LIMIT 1;

# fund user_id by max cost of order
select user_id from users_order where users_order.cost = ( select max(cost) from giftcertificate.users_order) group by user_id ;

#
#
#

select tag.id, COUNT(tag.id) as 'tagID', tag.name, user_id, uo.cost from tag LEFT JOIN gift_certificate_tags gct on tag.id = gct.tag_id
                                                                             LEFT JOIN gift_certificate gc on gct.gift_certificate_id = gc.id
                                                                             LEFT JOIN users_order_gift_certificate uogc on gc.id = uogc.gift_certificate_id
                                                                             LEFT JOIN users_order uo on uogc.order_id = uo.id
where user_id = 712 group by tag.id order by tagID DESC LIMIT 1;