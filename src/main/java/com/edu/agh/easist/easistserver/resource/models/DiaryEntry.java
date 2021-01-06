package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.resource.data.DiaryEntryData;
import com.edu.agh.easist.easistserver.resource.data.MedicineData;
import com.edu.agh.easist.easistserver.resource.data.SymptomEntryData;
import com.edu.agh.easist.easistserver.resource.utils.DateTimeParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
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
    private LocalDate date;
    @Column
    private String content;
    @Column
    private Integer mood;
    @OneToOne
    private SleepEntry sleepEntry;
    @OneToMany
    private Set<SymptomEntry> symptomEntries;
    @OneToMany
    private Set<MedicineEntry> medicineEntries;

    public DiaryEntry(DiaryEntryData diaryEntryData) {
        this.date = diaryEntryData.getDate();
        this.content = diaryEntryData.getContent();
        this.mood = diaryEntryData.getMood();
    }

    public void addSymptomEntry(SymptomEntry symptomEntry){
        if (this.symptomEntries == null)
            this.symptomEntries = new HashSet<>();
        this.symptomEntries.add(symptomEntry);
    }

    public void addMedicineEntry(MedicineEntry medicineEntry){
        if (this.medicineEntries == null)
            this.medicineEntries = new HashSet<>();
        this.medicineEntries.add(medicineEntry);
    }
}
