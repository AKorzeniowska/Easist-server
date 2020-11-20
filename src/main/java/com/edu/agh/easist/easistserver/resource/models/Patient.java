package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
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
    @Column(nullable = false)
    private String username;

    @OneToOne
    private User user;
    @ManyToOne
    private Doctor doctor;
    @OneToMany
    private Set<Appointment> appointments;
    @OneToMany
    private Set<DiaryEntry> diaryEntries;
    @OneToMany
    private Set<Symptom> symptoms;

    public Patient(UserData userData){
        this.firstName = userData.getFirstName();
        this.lastName = userData.getLastName();
        this.age = userData.getAge();
        this.phoneNumber = userData.getPhoneNumber();
        this.email = userData.getEmailAddress();
        this.username = userData.getUsername();
    }

    public void addSymptom(Symptom symptom){
        if (this.symptoms == null)
            this.symptoms = new HashSet<>();
        this.symptoms.add(symptom);
    }
}
