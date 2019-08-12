package com.gepardec.so.challenge.backend.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@SequenceGenerator(sequenceName = "state_id_seq", name = "state_id_seq", allocationSize = 1)
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "state_seq_id")
    private Long id;

    private String name;

    public State() {
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
        State state= (State) o;
        return Objects.equals(id, state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
