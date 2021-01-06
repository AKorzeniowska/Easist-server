package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.resource.data.SymptomEntryData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymptomEntry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Integer intensity;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private String comment;
    @ManyToOne
    private Symptom symptom;

    public SymptomEntry(SymptomEntryData symptom) {
        this.intensity = symptom.getIntensity();
        this.comment = symptom.getComment();
        this.date = symptom.getDate();
    }
}
