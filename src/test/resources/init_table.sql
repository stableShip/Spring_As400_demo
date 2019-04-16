CREATE TABLE key_sentence (
	id INTEGER NOT NULL,
	tagId INTEGER,
	type VARCHAR(80) NOT NULL,
	content VARCHAR(3000) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE secure_data (
	id INTEGER NOT NULL,
	tagId INTEGER,
	security VARCHAR(80),
	depositor VARCHAR(80),
	facilities VARCHAR(80),
	valuation VARCHAR(80),
	comments VARCHAR(80),
	tag VARCHAR(80) NOT NULL,
	PRIMARY KEY (id)
);

create table tag
(
	id INTEGER not null
		primary key,
	cordcustId VARCHAR(80) not null,
	cordcorType VARCHAR(80) not null,
	cordcrDate VARCHAR(80) not null,
	cordcrTime VARCHAR(80) not null
);




