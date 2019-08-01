package com.gepardec.so.challenge.backend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SequenceGenerator(sequenceName = "tag_id_seq", name = "tag_id_seq", allocationSize = 1)
public class Tag {

    public Tag() {

    }

    @Id
    @GeneratedValue(generator = "tag_id_seq")
    private Long id;

    private String name;

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
