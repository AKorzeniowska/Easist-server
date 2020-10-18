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
public class Patient {
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

    @OneToOne
    private User user;
    @ManyToOne
    private Doctor doctor;
    @OneToMany
    private Set<Appointment> appointments;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String email, String phoneNumer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumer;
    }
}
