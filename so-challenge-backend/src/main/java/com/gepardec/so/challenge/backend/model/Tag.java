package com.gepardec.so.challenge.backend.model;



import com.fasterxml.jackson.annotation.*;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "tag_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tagSet")
    private Set<Challenge> challengeSet;

    @JsonbTransient
    public Set<Challenge> getChallengeSet() {
        return challengeSet;
    }

    public void setChallengeSet(Set<Challenge> challengeSet) {
        this.challengeSet = challengeSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
