/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.db;

import com.gepardec.so.challenge.backend.model.Challenge;
import com.gepardec.so.challenge.backend.model.Participant;
import com.gepardec.so.challenge.backend.model.State;
import com.gepardec.so.challenge.backend.model.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author praktikant_ankermann
 */
public interface DAOLocal {

    /**
     * Persists the participant object into the database.
     *
     * @param p the participant to be added.
     * @return false if p is null, its primary key is null or it already exists
     * in the database.
     */
    boolean createParticipant(Participant p);

    /**
     * Persists the challenge object into the database. The id is given by the
     * database, since challenge has a sequence for that.
     *
     * @param c the challenge to be added.
     * @return false if c is null, already comes with an id or contains invalid
     * participants, true otherwise.
     */
    boolean createChallenge(Challenge c);

    /**
     * Reads all participants from the database.
     *
     * @return all participants as list.
     */
    List<Participant> getAllParticipants();

    /**
     * Reads all challenges from the database.
     *
     * @return all challenges as list.
     */
    List<Challenge> getAllChallenges();

    /**
     * Updates the challenge c by merging it into the database.
     *
     * @param c the challenge to be merged/updated
     * @return null if c is null or does not exist in the database, c otherwise.
     */
    Challenge updateChallenge(Challenge c);

    /**
     * Deletes a participant with the given id from the database. Also removes
     * the participant from every challenge he takes part in.
     *
     * @param profileId the id of the participant
     * @return null if no participant with this id could be found, p otherwise.
     */
    Participant deleteParticipant(Long profileId);

    /**
     * Deletes a challenge with the given id from the database. Participants are
     * not affected by this, they simply do not belong to this challenge
     * anymore.
     *
     * @param id the id of the challenge
     * @return null if no challenge with this id could be found, c otherwise.
     */
    Challenge deleteChallenge(Long id);

    /**
     * Tries to find a participant with the given id.
     *
     * @param profileId the id of the participant
     * @return null if no participant with this id could be found, otherwise the
     * participant.
     */
    Participant getParticipantById(Long profileId);

    /**
     * Tries to find a challenge with the given id.
     *
     * @param challengeId the id of the challenge
     * @return null if no challenge with this id could be found, otherwise the
     * challenge.
     */
    Challenge getChallengeById(Long challengeId);

    Challenge getChallengeByName(String name);

    boolean addParticipantToChallenge(String name, Long participantId);

    /**
     * Adds a participant to a challenge
     *
     * @param challengeId   the id of the challenge
     * @param participantId the id of the participant
     * @return false if no challenge or participant with the given ids could be
     * found or the participant already is part of this challenge, true
     * otherwise.
     */
    boolean addParticipantToChallenge(Long challengeId, Long participantId);

    boolean addTagsToChallenge(Long challengeId, Long tagId);

    Tag findTag(Long tagId);

    Tag deleteTag(long tagId);

    boolean removeTagFromChallenge(Long challengeId, Long tagId);

    /**
     * Removes a participant from a challenge
     *
     * @param challengeId   the id of the challenge
     * @param participantId the id of the participant
     * @return false if no challenge or participant with the given ids could be
     * found or the participant is not part of this challenge, true otherwise
     */
    boolean removeParticipantFromChallenge(Long challengeId, Long participantId);

    List<State> getAllStates();

    List<State> getCreateStates();

    List<State> getCreateStates(int stateId);

    Set<Participant> getParticipantsOfChallenge(Long challengeId);

    List<Tag> getAllTags();

    Tag deleteTag(Long profileId);

    boolean createTag(Tag t);

    boolean isTagNameAlreadyPresent(String name);

    boolean addParticipantToChallenge(Challenge c, String profileId);

    boolean addTagsToChallenge(Challenge c, String tagIds);

    Set<Tag> getTagsOfChallenge(Long chId);
}
