package com.edu.agh.easist.easistserver.resource.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntryData {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String content;
    private Integer mood;
    private SleepEntryData sleepEntry;
    private List<SymptomEntryData> symptomEntries;
    private List<MedicineEntryData> medicineEntries;
}
