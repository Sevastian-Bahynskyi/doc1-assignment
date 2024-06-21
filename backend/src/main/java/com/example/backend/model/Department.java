package com.example.backend.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId
    private String name;

    // Public constructor
    public Department(String name) {
        this.name = name;
    }

    // Default constructor for JPA
    public Department() {}

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
