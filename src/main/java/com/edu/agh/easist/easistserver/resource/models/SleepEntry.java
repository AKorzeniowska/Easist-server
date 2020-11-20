package com.edu.agh.easist.easistserver.resource.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
