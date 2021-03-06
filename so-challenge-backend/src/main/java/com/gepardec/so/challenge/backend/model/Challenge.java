/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author praktikant_ankermann
 */
@Entity
@SequenceGenerator(sequenceName = "ch_id_seq", name = "ch_id_seq", allocationSize = 1)
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ch_id_seq")
    @Basic(optional = false)
    private Integer id;

    @Size(max = 500)
    private String title;

    @Size(max = 500)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tagSet = new LinkedHashSet<>();

    private String award1;

    private String award2;

    private String award3;

    @ManyToOne(fetch = FetchType.EAGER)
    private Admin creator;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Participant> participantSet = new LinkedHashSet<>();

    public Challenge() {
    }

    public Admin getCreator() {
        return creator;
    }

    public void setCreator(Admin creator) {
        this.creator = creator;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
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

    public Set<Participant> getParticipantSet() {
        return participantSet;
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
}
