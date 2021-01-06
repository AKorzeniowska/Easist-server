package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.resource.models.*;
import com.edu.agh.easist.easistserver.resource.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.*;

@Controller
@Validated
@RequestMapping(path="/symptomEntry")
public class SymptomEntryController {
    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private SymptomEntryRepository symptomEntryRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(path = "/{username}/{symptomId}")
    @ResponseBody
    public Iterable<SymptomEntry> getAllSymptomEntriesForSymptom(Authentication authentication,
                                                                 @PathVariable("username") String username,
                                                                 @PathVariable("symptomId") Long symptomId){
        if (authentication.getName().equals(username)){
            Optional<Symptom> symptom = symptomRepository.findById(symptomId);
            return symptom.map(value -> symptomEntryRepository.findBySymptom(value)).orElse(null);
        }
        return null;
    }

    @GetMapping(path = "/{username}/symptomEntries")
    @ResponseBody
    public Map<LocalDate, List<SymptomEntry>> getAllSymptomEntriesByDate(Authentication authentication,
                                                                             @PathVariable("username") String username){
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (authentication.getName().equals(username) || (
                doctor.isPresent() && doctor.get().getPatients().stream().anyMatch(x -> x.getUsername().equals(username)))){
            Map<LocalDate, List<SymptomEntry>> data = new HashMap<>();
            Optional<Patient> patient = patientRepository.findByUsername(username);
            if (patient.isPresent()){
                Set<Symptom> symptoms = patient.get().getSymptoms();
                for(Symptom symptom : symptoms){
                    List<SymptomEntry> entries = symptomEntryRepository.findBySymptom(symptom);
                    for (SymptomEntry entry : entries){
                        if (!data.containsKey(entry.getDate())){
                            data.put(entry.getDate(), new ArrayList<>());
                        }
                        data.get(entry.getDate()).add(entry);
                    }
                }
                return data;
            }
        }
        return null;
    }
}
