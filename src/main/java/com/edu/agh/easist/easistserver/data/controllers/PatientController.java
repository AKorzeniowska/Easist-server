package com.edu.agh.easist.easistserver.data.controllers;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.repositories.RoleRepository;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.data.models.Patient;
import com.edu.agh.easist.easistserver.data.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@Validated
@RequestMapping(path="/patient")
public class PatientController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/username")
    @ResponseBody
    public Patient currentUserName(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user.isPresent()) {
            Optional<Patient> patient = patientRepository.findByUser(user.get());
            if (patient.isPresent())
                return patient.get();
        }
        return null;
    }
}
