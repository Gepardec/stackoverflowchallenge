/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gepardec.so.challenge.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Column(name = "id")
    private Integer id;
    @Size(max = 500)
    @Column(name = "title")
    private String title;
    @Column(name = "begindate")
    @Temporal(TemporalType.DATE)
    private Date begindate = new Date();
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate = new Date();
    
    @JoinTable(name = "challenge_participant", joinColumns = {
        @JoinColumn(name = "challenge_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "participant_id", referencedColumnName = "profileid")})
    @ManyToMany
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

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
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
