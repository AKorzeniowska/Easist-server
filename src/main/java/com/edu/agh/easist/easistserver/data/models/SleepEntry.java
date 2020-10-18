package com.edu.agh.easist.easistserver.data.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
public class SleepEntry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Time timeFrom;
    @Column(nullable = false)
    private Time timeTo;
    @Column(nullable = false)
    private Double hourCount;
}
