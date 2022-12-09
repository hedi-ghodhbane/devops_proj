package com.example.devopsproject.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Owner(Long id,String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Owner() {
    }


}
