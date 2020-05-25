package com.diego.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Rower extends AbstractEntity implements Cloneable {

    public enum Year {
        Freshmen, Sophomore, Junior, Senior
    }

    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";

    @NotNull
    @NotEmpty
    private String test = "";

    @ManyToOne
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Rower.Year year;

    @Email
    @NotNull
    @NotEmpty
    private String email = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}