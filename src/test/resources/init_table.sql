CREATE TABLE key_sentence
(
  id      INTEGER       NOT NULL,
  tagId   INTEGER,
  type    VARCHAR(80)   NOT NULL,
  content VARCHAR(3000) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE secure_data
(
  id         INTEGER     NOT NULL,
  tagId      INTEGER,
  security   VARCHAR(80),
  depositor  VARCHAR(80),
  facilities VARCHAR(80),
  valuation  VARCHAR(80),
  comments   VARCHAR(80),
  tag        VARCHAR(80) NOT NULL,
  PRIMARY KEY (id)
);

create table tag
(
  id           INTEGER     not null
    primary key,
  cordcustId   VARCHAR(80) not null,
  cordcorType  VARCHAR(80),
  cordcrDate   VARCHAR(80),
  cordcrTime   VARCHAR(80),
  remark       VARCHAR(80),
  serialNo     VARCHAR(80),
  customerName VARCHAR(80),
  createdAt    BIGINT
);

create table user
(
  id         INTEGER    not null auto_increment
    primary key,
  name       VARCHAR(80) not null,
  password   VARCHAR(80),
  role       VARCHAR(80) not null,
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

CREATE SCHEMA xxx;
create table xxx.GCCORDP
(
  id         INTEGER     not null
    primary key,
  CORDCUSTID VARCHAR(80) not null,
  CORDCRDATE VARCHAR(80),
  CORDCORTYP VARCHAR(80),
  CORDCRTIME VARCHAR(80),
  GCCORDP    VARCHAR(80),
  CORDBLKNUM VARCHAR(80),
  createdAt  BIGINT
);



