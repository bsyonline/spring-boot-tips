drop table if exists t_user;
create table t_user
(
    id     int auto_increment
        primary key,
    name   varchar(50) not null,
    age    int         null,
    gender tinyint     null,
    skill  varchar(10) null
);

drop table if exists t_employee;
create table t_employee
(
    id      int auto_increment primary key,
    name    varchar(50) not null,
    dept_id int         null
);

drop table if exists t_dept;
create table t_dept
(
    dept_id   int auto_increment primary key,
    dept_name varchar(50) not null
);

insert into t_dept (dept_name)
values ('销售部'),
       ('研发部'),
       ('行政部');
insert into t_employee (name, dept_id)
values ('Rose', 1),
       ('Jack', 1),
       ('Tom', 2),
       ('Alice', 3);