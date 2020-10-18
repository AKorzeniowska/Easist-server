package com.edu.agh.easist.easistserver.auth.models;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String roleName;

    public Role(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role() {
    }
}
