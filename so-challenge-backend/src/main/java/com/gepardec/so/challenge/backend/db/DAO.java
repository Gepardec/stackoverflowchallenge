/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.db;

import com.gepardec.so.challenge.backend.model.Challenge;
import com.gepardec.so.challenge.backend.model.Participant;
import com.gepardec.so.challenge.backend.model.Status;
import com.gepardec.so.challenge.backend.model.Tag;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author praktikant_ankermann
 */
@Stateless
public class DAO implements DAOLocal {

    @PersistenceContext
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
        if (c == null || findChallenge(c.getId()) == null) {
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
                c = findChallenge(c.getId());
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
    public Challenge deleteChallenge(Integer id) {
        Challenge c = findChallenge(id);
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
    public Challenge findChallenge(Integer challengeId) {
        return em.find(Challenge.class, challengeId);
    }

    @Override
    public boolean addParticipantToChallenge(Integer challengeId, Long participantId) {
        Challenge c = findChallenge(challengeId);
        Participant p = findParticipant(participantId);

        if (c == null || p == null || c.getParticipantSet().contains(p)) {
            return false;
        } else {
            c.addParticipant(p);
            return true;
        }
    }

    @Override
    public boolean removeParticipantFromChallenge(Integer challengeId, Long participantId) {
        Challenge c = findChallenge(challengeId);
        Participant p = findParticipant(participantId);

        if (c == null || p == null || !c.getParticipantSet().contains(p)) {
            return false;
        } else {
            c.getParticipantSet().remove(p);
            return true;
        }
    }

    @Override
    public List<Status> getAllStatuses() {
        return em.createQuery("SELECT s FROM Status s ORDER BY s.id", Status.class).getResultList();
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
                c = findChallenge(c.getId());
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
