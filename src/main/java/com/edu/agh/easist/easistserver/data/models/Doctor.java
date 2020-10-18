package com.edu.agh.easist.easistserver.data.models;

import com.edu.agh.easist.easistserver.auth.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@ToString
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;

    @OneToOne
    private User user;
    @OneToMany
    private Set<Patient> patients;
    @OneToMany
    private Set<Appointment> appointments;
}
