package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.resource.data.MedicineData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String dose;

    public Medicine(MedicineData medicineData){
        this.name = medicineData.getName();
        this.dose = medicineData.getDose();
    }
}
