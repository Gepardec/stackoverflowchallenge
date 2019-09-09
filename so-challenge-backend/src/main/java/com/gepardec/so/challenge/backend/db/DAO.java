/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.db;

import com.gepardec.so.challenge.backend.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;

/**
 * @author praktikant_ankermann
 */
@RequestScoped
public class DAO implements DAOLocal {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public boolean createParticipant(Participant p) {
        if (p == null || p.getProfileId() == null || getParticipantById(p.getProfileId()) != null) {
            return false;
        } else {
                em.persist(p);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean createChallenge(Challenge c) {
        if (c == null || c.getId() != null) {
            return false;
        } else {
            for (Participant p : c.getParticipantSet()) {
                if (p == null || p.getProfileId() == null || getParticipantById(p.getProfileId()) == null) {
                    return false;
                }
            }
            em.persist(c);
            return true;
        }
    }

    @Override
    public List<Participant> getAllParticipants() {
        return em.createQuery("SELECT p FROM Participant p ORDER BY p.profileId", Participant.class).getResultList();
    }

    @Override
    public List<Challenge> getAllChallenges() {
        return em.createQuery("SELECT c FROM Challenge c", Challenge.class).getResultList();
    }

    @Override
    @Transactional
    public Challenge updateChallenge(Challenge c) {
        if (c == null || getChallengeById(c.getId()) == null) {
            return null;
        } else {
            em.merge(c);
            return c;
        }
    }

    @Override
    @Transactional
    public Participant deleteParticipant(Long profileId) {
        Participant p = getParticipantById(profileId);
        if (p == null) {
            return null;
        } else {
            for (Challenge c : getAllChallenges()) {
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
    @Transactional
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
    public Participant getParticipantById(Long profileId) {
        return em.find(Participant.class, profileId);
    }

    @Override
    public Challenge getChallengeById(Long challengeId) {
        return em.find(Challenge.class, challengeId);
    }

    /**
     * returns the challenge with given name
     * @param name the title of the challenge
     * @return Challenge
     */
    @Override
    public Challenge getChallengeByName(String name) {
       Challenge c = (Challenge) em.createQuery("SELECT c FROM Challenge c WHERE c.title = :name").setParameter("name", name).getSingleResult();
       return c;
    }

    /**
     * identify challenge by challenge.title
     * @param name the name of the challenge
     * @param participantId the id of the participant
     * @return
     */
    @Override
    @Transactional
    public boolean addParticipantToChallenge(String name, Long participantId) {
        Challenge c = getChallengeByName(name);
        Participant p = getParticipantById(participantId);

        if (c == null || p == null || c.getParticipantSet().contains(p)) {
            System.err.println("Challenge with title: " + name + " is null");
            return false;
        } else {
            c.addParticipant(p);
            p.getChallengeSet().add(c);
            em.merge(c);
            return true;
        }
    }

    /**
     * identify challenge by challenge.id
     * @param challengeId   the id of the challenge
     * @param participantId the id of the participant
     * @return
     */
    @Override
    @Transactional
    public boolean addParticipantToChallenge(Long challengeId, Long participantId) {
        Challenge c = getChallengeById(challengeId);
        Participant p = getParticipantById(participantId);

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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public boolean removeParticipantFromChallenge(Long challengeId, Long participantId) {
        Challenge c = getChallengeById(challengeId);
        Participant p = getParticipantById(participantId);

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
    @Transactional
    public Tag deleteTag(Long profileId) {
        Tag t = em.find(Tag.class, profileId);
        if (t == null) {
            return null;
        } else {
            for (Challenge c : getAllChallenges()) {
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
    @Transactional
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

    //TODO rename
    @Override
    @Transactional
    public boolean addParticipantToChallenge(Challenge c, String profileIds) {
        if (c == null || c.getId() != null) {
            return false;
        } else {
            for (Participant p : c.getParticipantSet()) {
                if (p == null || p.getProfileId() == null || getParticipantById(p.getProfileId()) == null) {
                    return false;
                }
            }
            em.persist(c);

            String[] profileIdsArray = profileIds.split(":");

            List<Long> profileIdsList = new ArrayList<>();
            for (String profileId : profileIdsArray) {
                try {
                    profileIdsList.add(Long.parseLong(profileId));
                } catch (NumberFormatException nfe) {
                    return false;
                }
            }

            for (Long profileId : profileIdsList) {
                this.addParticipantToChallenge(c.getId(), profileId);
            }

            return true;
        }
    }

    @Override
    @Transactional
    public boolean addTagsToChallenge(Challenge c, String tagIds) {
        if (c == null) { // || c.getId() != null
            return false;
        } else {
            for (Tag t : c.getTagSet()) {
                if (t == null) {
                    //return false;
                }
            }
            em.merge(c);

            String[] tagIdsArray = tagIds.split(":");

            List<Long> tagIdsList = new ArrayList<>();
            for (String ids : tagIdsArray) {
                try {
                    tagIdsList.add(Long.parseLong(ids));
                } catch (NumberFormatException nfe) {
                    return false;
                }
            }

            for (Long profileId : tagIdsList) {
                this.addTagsToChallenge(c.getId(), profileId);
            }
            return true;
        }
    }
}
