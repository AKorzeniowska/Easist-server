package com.edu.agh.easist.easistserver.data.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Symptom {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String comment;
}
