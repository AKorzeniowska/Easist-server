package com.edu.agh.easist.easistserver.resource.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Boolean editable;
    @ManyToOne
    private Patient patient;
}
