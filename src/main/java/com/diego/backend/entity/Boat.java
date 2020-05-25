package com.diego.backend.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Boat extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private List<Rower> rowers = new LinkedList<>();

    public Boat() {
    }

    public Boat(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rower> getRowers() {
        return rowers;
    }
}
