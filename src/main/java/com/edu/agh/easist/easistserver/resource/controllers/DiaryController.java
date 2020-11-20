package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.resource.models.DiaryEntry;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
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
@RequestMapping(path="/diary")
public class DiaryController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping(value = "/all")
    @ResponseBody
    public Iterable<DiaryEntry> getAllDiaryEntries(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user.isPresent() && user.get().getRoles().stream().anyMatch(o -> o.getRoleName().equals("patient"))) {
            Optional<Patient> patient = patientRepository.findByUser(user.get());
            if (patient.isPresent())
                return patient.get().getDiaryEntries();
        }
        return null;
    }
}
