Drop table if exists admin cascade;
Drop table if exists challenge_tag cascade;
Drop table if exists tag cascade;
Drop table if exists challenge_participant cascade;
Drop table if exists participant cascade;
Drop table if exists challenge cascade;
Drop table if exists state cascade;
Drop table if exists status cascade;


 
CREATE TABLE IF NOT EXISTS admin(
	id serial,
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
    	id serial NOT NULL,
    	title varchar(500),
	description varchar(500),
    	fromDate date,
   	toDate date,
	state_id int REFERENCES state (id) ON UPDATE CASCADE ON DELETE CASCADE, 
	award1 varchar(500),
	award2 varchar(500),
	award3 varchar(500),
   	PRIMARY KEY( id )
);

CREATE TABLE IF NOT EXISTS participant (
  	profileId bigint, -- id from Stackoverflow
 	imageURL varchar(500),
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


