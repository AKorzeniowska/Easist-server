package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.auth.models.Role;
import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.resource.data.SymptomData;
import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Medicine;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.models.Symptom;
import com.edu.agh.easist.easistserver.resource.repositories.DoctorRepository;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
import com.edu.agh.easist.easistserver.resource.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Validated
@RequestMapping(path="/symptom")
public class SymptomController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    @PostMapping(path="")
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

    @GetMapping(path="/{username}")
    public @ResponseBody Iterable<Symptom> getAllSymptomsByUser(Authentication authentication,
                                                                @PathVariable("username") String username){
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        Optional<Patient> patient = patientRepository.findByUsername(username);
        if (patient.isEmpty())
            return null;
        if (patient.get().getUsername().equals(username) ||
                (doctor.isPresent() && doctor.get().getPatients().stream().anyMatch(x -> x.getUsername().equals(username))))
            return patient.get().getSymptoms();
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Void> deleteSymptom(Authentication authentication, @PathVariable("id") Long id){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isPresent()){
            Optional<Symptom> symptom = patient.get().getSymptoms().stream()
                    .filter(x -> x.getId().equals(id)).findFirst();
            if (symptom.isPresent()) {
                symptomRepository.delete(symptom.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
