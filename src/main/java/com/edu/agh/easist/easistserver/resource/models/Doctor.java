package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private String address1;
    @Column(nullable = false)
    private String address2;
    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @OneToMany
    private Set<Patient> patients;

    public Doctor(UserData userData){
        this.firstName = userData.getFirstName();
        this.lastName = userData.getLastName();
        this.email = userData.getEmailAddress();
        this.address1 = userData.getAddress1();
        this.address2 = userData.getAddress2();
        this.phoneNumber = userData.getPhoneNumber();
        this.username = userData.getUsername();
    }

    public void addPatient(Patient patient){
        if (this.patients == null)
            this.patients = new HashSet<>();
        this.patients.add(patient);
    }
}
