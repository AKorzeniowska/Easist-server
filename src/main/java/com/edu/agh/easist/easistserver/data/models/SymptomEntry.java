package com.edu.agh.easist.easistserver.data.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class SymptomEntry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Integer intensity;
    @Column
    private String comment;
    @ManyToOne
    private Symptom symptom;
}
