/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.model;


import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.Set;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author praktikant_ankermann
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    private Long profileId;

    @Size(max = 500)
    @Column(name = "link")
    private String link;

    @Size(max = 300)
    @Column(name = "username")
    private String username;

    @Column(name = "imageurl")
    private String imageURL;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "participantSet")
    private Set<Challenge> challengeSet;

    @JsonbTransient
    public Set<Challenge> getChallengeSet() {
        return challengeSet;
    }

    @JsonbTransient
    public void setChallengeSet(Set<Challenge> challengeSet) {
        this.challengeSet = challengeSet;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Participant() {
    }

    public Participant(Long profileId) {
        this.profileId = profileId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profileId != null ? profileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participant)) {
            return false;
        }
        Participant other = (Participant) object;
        if (((this.profileId == null) && (other.profileId != null)) || ((this.profileId != null) && !this.profileId.equals(other.profileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gepardec.so.challenge.backend.model.Participant[ profileid=" + profileId + " ]";
    }

}
