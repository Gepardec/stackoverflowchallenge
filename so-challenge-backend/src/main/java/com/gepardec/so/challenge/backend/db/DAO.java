/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.db;

import com.gepardec.so.challenge.backend.model.*;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author praktikant_ankermann
 */
@RequestScoped
public class DAO implements DAOLocal {

    @Inject
    private EntityManager em;

    @Override
    public boolean createParticipant(Participant p) {
        if (p == null || p.getProfileId() == null || findParticipant(p.getProfileId()) != null) {
            return false;
        } else {
            em.persist(p);
            return true;
        }
    }

    @Override
    public boolean createChallenge(Challenge c) {
        if (c == null || c.getId() != null) {
            return false;
        } else {
            for (Participant p : c.getParticipantSet()) {
                if (p == null || p.getProfileId() == null || findParticipant(p.getProfileId()) == null) {
                    return false;
                }
            }
            em.persist(c);
            return true;
        }
    }

    @Override
    public List<Participant> readAllParticipants() {
        return em.createQuery("SELECT p FROM Participant p ORDER BY p.profileId", Participant.class).getResultList();
    }

    @Override
    public List<Challenge> readAllChallenges() {
        return em.createQuery("SELECT c FROM Challenge c ORDER BY c.id", Challenge.class).getResultList();
    }

    @Override
    public Challenge updateChallenge(Challenge c) {
        if (c == null || getChallengeById(c.getId()) == null) {
            return null;
        } else {
            em.merge(c);
            return c;
        }
    }

    @Override
    public Participant deleteParticipant(Long profileId) {
        Participant p = findParticipant(profileId);
        if (p == null) {
            return null;
        } else {
            for (Challenge c : readAllChallenges()) {
                c = getChallengeById(c.getId());
                if (c != null && c.getParticipantSet().contains(p)) {
                    c.getParticipantSet().remove(p);
                    em.merge(c);
                }
            }
            em.remove(p);
            return p;
        }
    }

    @Override
    public Challenge deleteChallenge(Long id) {
        Challenge c = getChallengeById(id);
        if (c == null) {
            return null;
        } else {
            em.remove(c);
            return c;
        }
    }

    @Override
    public Participant findParticipant(Long profileId) {
        return em.find(Participant.class, profileId);
    }

    @Override
    public Challenge getChallengeById(Long challengeId) {
        return em.find(Challenge.class, challengeId);
    }

    @Override
    public boolean addParticipantToChallenge(Long challengeId, Long participantId) {
        Challenge c = getChallengeById(challengeId);
        Participant p = findParticipant(participantId);

        if (c == null || p == null || c.getParticipantSet().contains(p)) {
            return false;
        } else {
            c.addParticipant(p);
            p.getChallengeSet().add(c);
            em.merge(c);
            return true;
        }
    }

    @Override
    public boolean addTagsToChallenge(Long challengeId, Long tagId) {
        Challenge c = getChallengeById(challengeId);
        Tag t = findTag(tagId);

        if(t == null || c == null || c.getTagSet().contains(t)) {
            return false;
        } else {
            c.addTag(t);
            t.getChallengeSet().add(c);
            em.merge(c);
            return true;
        }
    }

    public Tag findTag(Long tagId) {
        return em.find(Tag.class, tagId);
    }

    @Override
    public Tag deleteTag(long tagId) {
        Tag t = findTag(tagId);
        if(t == null) {
            return null;
        } else {
            em.remove(t);
            return t;
        }
    }

    @Override
    public boolean removeTagFromChallenge(Long challengeId, Long tagId) {
        Challenge c = getChallengeById(challengeId);
        if (c.getTagSet().contains(findTag(tagId))) {
            c.getTagSet().remove(findTag(tagId));
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean removeParticipantFromChallenge(Long challengeId, Long participantId) {
        // TODO also remove participants from the challenge_participants table
        Challenge c = getChallengeById(challengeId);
        Participant p = findParticipant(participantId);

        if (c == null || p == null || !c.getParticipantSet().contains(p)) {
            return false;
        } else {
            c.getParticipantSet().remove(p);
            return true;
        }
    }

    @Override
    public List<State> getAllStates() {
        return em.createQuery("SELECT s FROM State s ORDER BY s.id", State.class).getResultList();
    }

    @Override
    public List<State> getAvailableStates() {
        return em.createQuery("SELECT s FROM State s WHERE s.id = 1 OR s.id = 4", State.class).getResultList();
    }

    @Override
    public List<State> getAvailableStates(State state) {
        switch (state.getName()) {
            case "planned" :
                return em.createQuery("SELECT s FROM State s WHERE s.id = 1 OR s.id = 3 OR s.id = 4", State.class).getResultList();
            case "active":
                return em.createQuery("SELECT s FROM State s WHERE s.id = 2 OR s.id = 3 OR s.id = 1", State.class).getResultList();
            case "canceled":
                return em.createQuery("SELECT s FROM State s WHERE s.id = 3", State.class).getResultList();
            case "completed":
                return em.createQuery("SELECT s FROM State s WHERE s.id = 2", State.class).getResultList();
        }
        return null;
    }
    // TODO REVIEW QUERY
    // figure out how to query bridge table -> Select profileid, imageurl, link, username from participant as p
    //INNER JOIN challenge_participant as cp On p.profileid = cp.participantid
    //INNER JOIN challenge c on cp.challengeid = c.id -> does not work, because "challenge_participants" does not get recognized as table/entity
    @Override
    public List<Participant> getParticipantsOfChallenge(Long challengeId) {
        if(em.find(Challenge.class, challengeId) == null) return null;
        return  em.createQuery("SELECT DISTINCT p FROM Participant p, Challenge c JOIN Challenge.participantSet part where part.profileId = :challengeId", Participant.class).getResultList();
    }

    @Override
    public List<Tag> getAllTags() {
        return em.createQuery("SELECT t FROM Tag t ORDER BY t.name", Tag.class).getResultList();
    }

    @Override
    public Tag deleteTag(Long profileId) {
        Tag t = em.find(Tag.class, profileId);
        if (t == null) {
            return null;
        } else {
            for (Challenge c : readAllChallenges()) {
                c = getChallengeById(c.getId());
                if (c != null && c.getParticipantSet().contains(t)) {
                    c.getTagSet().remove(t);
                    em.merge(c);
                }
            }
            em.remove(t);
            return t;
        }
    }

    @Override
    public boolean createTag(Tag t) {
        if (t == null || t.getId() != null) {
            return false;
        } else {
            em.persist(t);
            return true;
        }
    }

    @Override
    public boolean isTagNameAlreadyPresent(String name) {
        return !em.createQuery("SELECT t FROM Tag t WHERE t.name = :name")
                .setParameter("name", name)
                .getResultList()
                .isEmpty();
    }
}
