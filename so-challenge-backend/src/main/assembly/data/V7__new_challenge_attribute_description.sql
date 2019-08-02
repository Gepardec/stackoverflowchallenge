DROP TABLE IF EXISTS challenge_participant CASCADE;
DROP TABLE IF EXISTS challenge_status CASCADE;
DROP TABLE IF EXISTS participant CASCADE;
DROP TABLE IF EXISTS challenge CASCADE;
DROP TABLE IF EXISTS status CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS admin CASCADE;

CREATE TABLE IF NOT EXISTS admin(
	id serial,
	PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS status (
id serial, 
name varchar(500), 
	PRIMARY KEY(id)
); 

CREATE TABLE IF NOT EXISTS challenge (
    	id serial,
    	title varchar(500),
	description varchar(500),
    	beginDate date,
   	endDate date,
	award1 varchar(500),
	award2 varchar(500),
	award3 varchar(500),
   	PRIMARY KEY( id )
);

CREATE TABLE IF NOT EXISTS participant (
  	profileId bigint,
 	link varchar(500),
  	username varchar(500),
   	PRIMARY KEY( profileId )
);


CREATE TABLE IF NOT EXISTS tag (
	id serial,
	name varchar(500),
	challenge_id int REFERENCES challenge (id) ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY( id )
);

CREATE TABLE IF NOT EXISTS challenge_participant (
   	challenge_id int REFERENCES challenge (id) ON UPDATE CASCADE ON DELETE CASCADE ,
   	participant_id int REFERENCES participant (profileId) ON UPDATE CASCADE ON DELETE CASCADE,
    	CONSTRAINT challenge_participant_pkey PRIMARY KEY ( challenge_id, participant_id ) -- explicit pk
);

CREATE TABLE IF NOT EXISTS challenge_status (
	status_id int REFERENCES status (id) ON UPDATE CASCADE On DELETE CASCADE,
	challenge_id int REFERENCES challenge (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT challenge_status_pkey PRIMARY KEY ( challenge_id, status_id )
);



