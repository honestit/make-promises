create table promises
(
    id   int8         not null,
    who  varchar(255) not null,
    whom varchar(255) not null,
    what text         not null,
    kept boolean,
    primary key (id)
);

create table promises_deadlines
(
    id     int8      not null,
    "when" timestamp not null,
    till   timestamp not null,
    pass   timestamp not null,
    primary key (id),
    foreign key (id) references promises (id)
);
