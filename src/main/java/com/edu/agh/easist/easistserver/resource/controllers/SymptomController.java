package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.auth.models.Role;
import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.resource.data.SymptomData;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.models.Symptom;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
import com.edu.agh.easist.easistserver.resource.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Validated
@RequestMapping(path="/symptom")
public class SymptomController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private SymptomRepository symptomRepository;

    @Transactional
    @PostMapping(path="/add")
    public ResponseEntity<Void> addSymptom(Authentication authentication, @RequestBody SymptomData symptomData){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Symptom symptom = new Symptom(symptomData);
        symptomRepository.save(symptom);
        patient.get().addSymptom(symptom);
        patientRepository.save(patient.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
