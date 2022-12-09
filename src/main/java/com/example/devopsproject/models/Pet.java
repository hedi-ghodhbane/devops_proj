package com.example.devopsproject.models;

import javax.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Pet(long id, String name, String description, Owner owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Owner owner;


    public Pet(){}

    public Pet(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Pet(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
