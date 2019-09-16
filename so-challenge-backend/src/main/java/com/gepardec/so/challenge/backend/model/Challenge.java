/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.model;

import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.*;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author praktikant_ankermann
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator = "challenge_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Size(max = 500)
    private String title;

    @Size(max = 500)
    private String description;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date fromDate;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date toDate;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private State state;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "challenge_participant",
            joinColumns = @JoinColumn(name = "challenge_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "profileId")
    )
    private Set<Participant> participantSet = new LinkedHashSet<>();

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "challenge_tag",
            joinColumns = @JoinColumn(name = "challenge_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tagSet = new LinkedHashSet<>();

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public Set<Participant> getParticipantSet() {
        return participantSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
    this.tagSet = tagSet;
    }

    private String award1;

    private String award2;

    private String award3;

    public Challenge() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State status) {
        this.state = status;
    }

    public String getAward1() {
        return award1;
    }

    public void setAward1(String award1) {
        this.award1 = award1;
    }

    public String getAward2() {
        return award2;
    }

    public void setAward2(String award2) {
        this.award2 = award2;
    }

    public String getAward3() {
        return award3;
    }

    public void setAward3(String award3) {
        this.award3 = award3;
    }

    public void setParticipantSet(Set<Participant> participantSet) {
        this.participantSet = participantSet;
    }

    public void addParticipant(Participant p) {
        if (p != null) {
            this.participantSet.add(p);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Challenge challenge = (Challenge) o;
        return Objects.equals(id, challenge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addTag(Tag t) {
        this.getTagSet().add(t);
    }
}
