-- The tables created here are reused for multiple examples.
-- Therefore they contain columns that are not used for other examples.

create table person
(
    id      uuid primary key,
    version int,
    name    varchar(255),
    lastname varchar(255)
);

create table color
(
    id      uuid primary key,
    version int,
    name    varchar(255)
);

create table minion
(
    id             uuid primary key,
    version        int,
    name           varchar(255),
    number_of_eyes varchar(255),
    color          uuid,
    evil_master    uuid,
    description    varchar(4000),
    constraint FK_minion_color foreign key (color) references color,
    constraint FK_minion_person foreign key (evil_master) references person
);


create table toy
(
    minion uuid not null,
    name   varchar(255),
    material varchar(255)
)
