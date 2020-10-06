package com.edu.agh.easist.easistserver.models;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean accountNonExpired;
    @Column(nullable = false)
    private Boolean accountNonLocked;
    @Column(nullable = false)
    private Boolean credentialsNonExpired;
    @Column(nullable = false)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public User() {
    }

    public void addRole(Role role){
        if (this.roles == null)
            this.roles = new HashSet<>();
        this.roles.add(role);
    }
}