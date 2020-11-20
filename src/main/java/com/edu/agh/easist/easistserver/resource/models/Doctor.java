package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@ToString
@Data
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

    @OneToOne
    private User user;
    @OneToMany
    private Set<Patient> patients;
    @OneToMany
    private Set<Appointment> appointments;

    public Doctor(UserData userData){
        this.firstName = userData.getFirstName();
        this.lastName = userData.getLastName();
        this.email = userData.getEmailAddress();
        this.address1 = userData.getAddress1();
        this.address2 = userData.getAddress2();
        this.phoneNumber = userData.getPhoneNumber();
        this.username = userData.getUsername();
    }
}
