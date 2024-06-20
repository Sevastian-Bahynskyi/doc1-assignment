package com.example.backend.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String headline;
    private String content;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private LocalDateTime postingTime;
    private int likes;

    // Public constructor
    public Story(String headline, String content, Department department) {
        this.headline = headline;
        this.content = content;
        this.department = department;
        this.postingTime = LocalDateTime.now();
        this.likes = 0;
    }

    // Default constructor for JPA
    protected Story() {}

    // Getters
    public int getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDateTime getPostingTime() {
        return postingTime;
    }

    public int getLikes() {
        return likes;
    }

    // Method to increment likes
    public void like() {
        this.likes++;
    }
}
