package com.problemfighter.pfspring.webtestmodule.example.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String details;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Comment> comments = new HashSet<>();

    public Post setName(String name) {
        this.name = name;
        return this;
    }

    public Post setDetails(String details) {
        this.details = details;
        return this;
    }
}
