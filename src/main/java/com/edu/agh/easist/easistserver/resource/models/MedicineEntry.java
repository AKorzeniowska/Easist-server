package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.resource.data.MedicineEntryData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineEntry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Boolean taken;
    @ManyToOne
    private Medicine medicine;

    public MedicineEntry(MedicineEntryData medicineEntryData){
        this.date = medicineEntryData.getDate();
        this.taken = medicineEntryData.getTaken();
    }
}
