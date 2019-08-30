DROP TABLE IF EXISTS challenge_participant CASCADE;
DROP TABLE IF EXISTS challenge_status CASCADE;
DROP TABLE IF EXISTS participant CASCADE;
DROP TABLE IF EXISTS challenge CASCADE;
DROP TABLE IF EXISTS status CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS admin CASCADE;


CREATE TABLE IF NOT EXISTS admin(
	id bigint,
	username varchar(500),
	password varchar(500),
	salt varchar(500),
	firstName varchar(500),
	lastName varchar(500),
	PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS state (
	id int,
	name varchar(500), 
	PRIMARY KEY(id)
); 

CREATE TABLE IF NOT EXISTS challenge (
    id bigint NOT NULL,
    title varchar(500),
	description varchar(500),
    fromDate date,
   	toDate date,
	state_id int REFERENCES state(id) ON DELETE CASCADE ON UPDATE CASCADE,
	award1 varchar(500),
	award2 varchar(500),
	award3 varchar(500),
   	PRIMARY KEY( id )
);

CREATE TABLE IF NOT EXISTS participant (
  	profileId bigint, -- id from Stackoverflow
 	imageURL varchar(500),
  	username varchar(500),
   	PRIMARY KEY( profileId )
);

CREATE TABLE IF NOT EXISTS tag (
	id bigint,
	name varchar(500),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS challenge_tag (
	challenge_id bigint REFERENCES challenge (id) ON UPDATE CASCADE ON DELETE CASCADE,
	tag_id bigint REFERENCES tag (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT challenge_tag_pkey PRIMARY KEY (challenge_id, tag_id)
);

CREATE TABLE IF NOT EXISTS challenge_participant (
   	challenge_id int REFERENCES challenge (id) ON UPDATE CASCADE ON DELETE CASCADE ,
   	participant_id int REFERENCES participant (profileId) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT challenge_participant_pkey PRIMARY KEY ( challenge_id, participant_id ) -- explicit pk
);

INSERT INTO state VALUES (1, 'active') ON CONFLICT DO NOTHING;
INSERT INTO state VALUES (2, 'completed') ON CONFLICT DO NOTHING;
INSERT INTO state VALUES (3, 'cancelled') ON CONFLICT DO NOTHING;
INSERT INTO state VALUES (4, 'planned') ON CONFLICT DO NOTHING;       

