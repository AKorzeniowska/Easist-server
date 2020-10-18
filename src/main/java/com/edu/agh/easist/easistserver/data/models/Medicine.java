package com.edu.agh.easist.easistserver.data.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
public class Medicine {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double dose;
    @Column
    private Integer frequency;
    @Column
    private Time time;
    @Column
    private Integer dosesLeft;
    @Column
    private String comment;
    @Column(nullable = false)
    private Boolean editable;
}
