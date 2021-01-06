package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Invitation;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.models.SymptomEntry;
import com.edu.agh.easist.easistserver.resource.repositories.DoctorRepository;
import com.edu.agh.easist.easistserver.resource.repositories.InvitationRepository;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Validated
@RequestMapping(path="/patient")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(value = "/{username}")
    @ResponseBody
    public Patient currentUserName(Authentication authentication, @PathVariable("username") String username) {
        if (authentication.getName().equals(username)) {
            Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
            return patient.orElse(null);
        }
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (doctor.isPresent() && doctor.get().getPatients().stream().anyMatch(x -> x.getUsername().equals(username))){
            Optional<Patient> patient = patientRepository.findByUsername(username);
            return patient.orElse(null);
        }
        return null;
    }

    @GetMapping(value = "doctor")
    @ResponseBody
    public Doctor getDoctor(Authentication authentication) {
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        return patient.map(Patient::getDoctor).orElse(null);
    }

    @GetMapping(value = "/search/{text}")
    @ResponseBody
    public Iterable<Patient> searchPatientByNameOrUsername(Authentication authentication,
                                                           @PathVariable("text") String searchText) {
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (doctor.isEmpty())
            return null;

        List<Patient> invitedPatients = invitationRepository.findByDoctorId(doctor.get().getId())
                .stream().map(Invitation::getPatient).collect(Collectors.toList());

        List<Patient> patientList = new ArrayList<>();

        List<Patient> patientsByLastName = patientRepository.findByLastNameIsContaining(searchText);
        patientsByLastName = patientsByLastName.stream().filter(x -> x.getDoctor() == null).collect(Collectors.toList());
        for (Patient patient : patientsByLastName){
            if (invitedPatients.stream().noneMatch(x -> x.getId().equals(patient.getId())))
                patientList.add(patient);
        }
        List<Patient> patientsByUsername = patientRepository.findByUsernameIsContaining(searchText);
        patientsByUsername = patientsByUsername.stream().filter(x -> x.getDoctor() == null).collect(Collectors.toList());
        for (Patient patient : patientsByUsername){
            if (patientList.stream().noneMatch(x -> x.getId().equals(patient.getId()))
                    && invitedPatients.stream().noneMatch(x -> x.getId().equals(patient.getId())))
                patientList.add(patient);
        }

        return patientList;
    }
}
