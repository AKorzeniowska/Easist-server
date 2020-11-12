package com.edu.agh.easist.easistserver.data.models;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@ToString
@Data
@NoArgsConstructor
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
    @Column(nullable = false)
    private Integer age;

    @OneToOne
    private User user;
    @ManyToOne
    private Doctor doctor;
    @OneToMany
    private Set<Appointment> appointments;
    @OneToMany
    private Set<DiaryEntry> diaryEntries;

    public Patient(String firstName, String lastName, String email, String phoneNumber, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public Patient(UserData userData){
        this.firstName = userData.getFirstName();
        this.lastName = userData.getLastName();
        this.age = userData.getAge();
        this.phoneNumber = userData.getPhoneNumber();
        this.email = userData.getEmailAddress();
    }
}
