CREATE TABLE IF NOT EXISTS challenge (
    id serial,
    title varchar(500),
    beginDate date,
    endDate date,
    PRIMARY KEY( id )
);

CREATE TABLE IF NOT EXISTS participant (
    profileId bigint,
    link varchar(500),
    username varchar(300),
    PRIMARY KEY( profileId )
);

CREATE TABLE IF NOT EXISTS challenge_participant (
    challenge_id int REFERENCES challenge (id) ON UPDATE CASCADE ON DELETE CASCADE ,
    participant_id int REFERENCES participant (profileId) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT challenge_participant_pkey PRIMARY KEY (challenge_id, participant_id) -- explicit pk
);
