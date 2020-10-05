package com.edu.agh.easist.easistserver.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Foo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Foo() {
    }

    public Foo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
