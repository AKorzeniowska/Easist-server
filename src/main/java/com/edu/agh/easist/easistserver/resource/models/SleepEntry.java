package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.resource.data.SleepEntryData;
import com.edu.agh.easist.easistserver.resource.utils.DateTimeParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepEntry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Time timeFrom;
    @Column(nullable = false)
    private Time timeTo;
    @Column(nullable = false)
    private Double hourCount;

    public SleepEntry(SleepEntryData sleepEntryData) {
        this.date = sleepEntryData.getDate();
        this.timeFrom = DateTimeParser.parseStringToTime(sleepEntryData.getTimeFrom());
        this.timeTo = DateTimeParser.parseStringToTime(sleepEntryData.getTimeTo());
        setHourCount();
    }

    public void setHourCount(){
        if (this.timeTo == null || this.timeFrom == null){
            return;
        }
        LocalTime from = timeFrom.toLocalTime();
        LocalTime to = timeTo.toLocalTime();
        Duration duration;
        if (to.isAfter(from))
            duration = Duration.between(from, to);
        else
            duration = Duration.between(to, from);
        double diff = (double)duration.toMinutes() / 60;
        this.hourCount = (double) Math.round(diff * 100) / 100;
    }
}
