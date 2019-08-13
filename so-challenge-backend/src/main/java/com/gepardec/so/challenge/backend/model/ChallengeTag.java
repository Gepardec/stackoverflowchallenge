package com.gepardec.so.challenge.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "challenge_tag")
public class ChallengeTag implements Serializable {

    private String id;

    Long challengeId;

    Long tagId;

    public ChallengeTag() {
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     public ChallengeTag(long cId, Long tId) {
        this.challengeId = cId;
        this.tagId = tId;
    }
}
