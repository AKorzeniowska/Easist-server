package com.edu.agh.easist.easistserver.resource.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Date date;
    @Column
    private String content;
    @Column
    private Integer mood;
    @OneToOne
    private SleepEntry sleepEntry;
    @OneToMany
    private Set<SymptomEntry> symptomEntries;
}
