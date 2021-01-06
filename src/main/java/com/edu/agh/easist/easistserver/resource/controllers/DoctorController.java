package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.Doc;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@Validated
@RequestMapping(path="/doctor")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(value = "")
    @ResponseBody
    public Doctor currentUsername(Authentication authentication){
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        return doctor.orElse(null);
    }

    @GetMapping(value = "/patients")
    @ResponseBody
    public Iterable<Patient> getPatients(Authentication authentication) {
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (doctor.isEmpty())
            return null;
        //has to be assigned first, do json annotation doesn't ignore it as part of Doctor class
        Set<Patient> patientSet = doctor.get().getPatients();
        return patientSet;
    }
}
