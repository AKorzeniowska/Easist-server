package com.edu.agh.easist.easistserver.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Integer age;
    private String address1;
    private String address2;
}
