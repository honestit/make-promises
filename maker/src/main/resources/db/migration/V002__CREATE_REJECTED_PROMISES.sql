create table rejected_promises
(
    id     int8         not null,
    who    varchar(255) not null,
    whom   varchar(255),
    what   text,
    "when" timestamp,
    till   timestamp,
    reason varchar(255) not null,
    primary key (id)
);
