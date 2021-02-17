drop database if exists user_db;
create database user_db;
drop table if exists user_db.t_user;
create table user_db.t_user
(
    id   int primary key auto_increment,
    name varchar(50) not null
);
insert into user_db.t_user (name)
values ('Tom'),
       ('Alice');

drop database if exists order_db;
create database order_db;
create table order_db.t_order
(
    order_no     int primary key,
    create_time timestamp,
    user_id     int not null
);
insert into order_db.t_order (order_no, create_time, user_id)
values (1, sysdate(), 1),
       (2, sysdate(), 2);
