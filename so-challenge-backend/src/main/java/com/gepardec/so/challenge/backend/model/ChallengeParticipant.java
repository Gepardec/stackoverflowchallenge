package com.gepardec.so.challenge.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "challenge_participant")
public class ChallengeParticipant  implements Serializable {

    private String id;

    private Long challengeId;

    private Long participantId;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChallengeParticipant() {}

    public ChallengeParticipant(Long cId, Long pId) {
        this.challengeId = cId;
        this.participantId = pId;
    }
}
