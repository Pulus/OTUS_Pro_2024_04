-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table address
(
    id   bigserial not null primary key,
    street varchar(50)
);

create table client
(
    id   bigserial not null primary key,
    name varchar(50),
    address_id bigserial references address(id)
);

create table phone
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigserial references client(id)
);
 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_seq start with 1 increment by 1;
create sequence address_seq start with 1 increment by 1;
create sequence phone_seq start with 1 increment by 1;

create table address
(
    id   bigint not null primary key,
    street varchar(50)
);

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint references address(id)
);

create table phone
(
    id   bigint not null primary key,
    number varchar(50),
    client_id bigint references client(id)
);
