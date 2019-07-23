/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author praktikant_ankermann
 */
@Entity
@Table(name = "challenge")
@XmlRootElement
@SequenceGenerator(sequenceName = "ch_id_seq", name = "ch_id_seq", allocationSize = 1)
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ch_id_seq")
    @Basic(optional = false)
    private Integer id;

    @Size(max = 500)
    private String title;

    @JsonbDateFormat
    private LocalDateTime begindate;

    @JsonbDateFormat
    private LocalDateTime enddate;

    private String award1;

    private String award2;

    private String award3;

    public String getAward3() {
        return award3;
    }

    public void setAward3(String award3) {
        this.award3 = award3;
    }

    public String getAward2() {
        return award2;
    }

    public void setAward2(String award2) {
        this.award2 = award2;
    }

    public String getAward1() {
        return award1;
    }

    public void setAward1(String award1) {
        this.award1 = award1;
    }

    @JoinTable(name = "challenge_participant", joinColumns = {
        @JoinColumn(name = "challenge_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "participant_id", referencedColumnName = "profileid")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Participant> participantList = new ArrayList<>(0);

    public Challenge() {
    }

    public Challenge(Integer id) {
        this.id = id;
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

    public LocalDateTime getBegindate() {
        return begindate;
    }

    public void setBegindate(LocalDateTime begindate) {
        this.begindate = begindate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Challenge)) {
            return false;
        }
        Challenge other = (Challenge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gepardec.so.challenge.backend.model.Challenge[ id=" + id + " ]";
    }

    public void addParticipant(Participant p) {
        if (p != null) {
            this.participantList.add(p);
        }
    }

}
