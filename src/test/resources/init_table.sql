
create table user
(
  id         INTEGER    not null auto_increment
    primary key,
  name       VARCHAR(80) not null,
  password   VARCHAR(80),
  role       VARCHAR(80) not null,
  status     INTEGER not null,
  created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP   NOT NULL DEFAULT NOW() ON UPDATE now()
);


create table menu
(
  id    INTEGER     not null
    primary key,
  route VARCHAR(80) not null,
  name  VARCHAR(80) not null
);

create table role_navigate
(
  id       INTEGER     not null
    primary key,
  role     VARCHAR(80) not null,
  menuName VARCHAR(80) not null
);
