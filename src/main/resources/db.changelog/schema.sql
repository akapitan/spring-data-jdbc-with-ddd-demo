create table person
(
    id       uuid primary key,
    version  int,
    name     varchar(255),
    lastname varchar(255)
);

create table color
(
    minion   uuid not null,
    name    varchar(255)
);

create table minion
(
    id             uuid primary key,
    version        int,
    name           varchar(255),
    number_of_eyes varchar(255),
    evil_master    uuid,
    description    jsonb,
    constraint FK_minion_person foreign key (evil_master) references person
);

create table toy
(
    minion   uuid not null,
    name     varchar(255),
    material varchar(255)
)
