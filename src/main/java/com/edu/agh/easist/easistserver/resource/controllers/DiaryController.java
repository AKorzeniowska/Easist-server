package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.resource.data.DiaryEntryData;
import com.edu.agh.easist.easistserver.resource.data.MedicineEntryData;
import com.edu.agh.easist.easistserver.resource.data.SymptomEntryData;
import com.edu.agh.easist.easistserver.resource.models.*;
import com.edu.agh.easist.easistserver.resource.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Validated
@RequestMapping(path="/diary")
public class DiaryController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private SymptomEntryRepository symptomEntryRepository;
    @Autowired
    private DiaryEntryRepository diaryEntryRepository;
    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private SleepEntryRepository sleepEntryRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private MedicineEntryRepository medicineEntryRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(path="/{username}")
    public @ResponseBody Iterable<DiaryEntry> getAllEntriesByUser(Authentication authentication,
                                                                @PathVariable("username") String username){
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (!username.equals(authentication.getName()) && (doctor.isEmpty()
                || doctor.get().getPatients().stream().noneMatch(x -> x.getUsername().equals(username)))) {
            return null;
        }
        Optional<Patient> patient = patientRepository.findByUsername(username);

        return patient.<Iterable<DiaryEntry>>map(Patient::getDiaryEntries).orElse(null);
    }

    @Transactional
    @PostMapping(path="/entry")
    public ResponseEntity<Void> addDiaryEntry(Authentication authentication, @RequestBody DiaryEntryData diaryEntryData){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        DiaryEntry entry = new DiaryEntry(diaryEntryData);
        System.out.println(diaryEntryData);
        for (SymptomEntryData symptomEntryData : diaryEntryData.getSymptomEntries()){
            Optional<Symptom> symptom = symptomRepository.findById(symptomEntryData.getSymptomId());
            if (symptom.isPresent()){
                SymptomEntry symptomEntry = new SymptomEntry(symptomEntryData);
                symptomEntry.setSymptom(symptom.get());
                symptomEntryRepository.save(symptomEntry);
                entry.addSymptomEntry(symptomEntry);
            }
        }

        for (MedicineEntryData medicineEntryData : diaryEntryData.getMedicineEntries()){
            Optional<Medicine> medicine = medicineRepository.findById(medicineEntryData.getMedicineId());
            if (medicine.isPresent()){
                MedicineEntry medicineEntry = new MedicineEntry(medicineEntryData);
                medicineEntry.setMedicine(medicine.get());
                medicineEntryRepository.save(medicineEntry);
                entry.addMedicineEntry(medicineEntry);
            }
        }

        SleepEntry sleepEntry = new SleepEntry(diaryEntryData.getSleepEntry());
        sleepEntryRepository.save(sleepEntry);
        entry.setSleepEntry(sleepEntry);
        diaryEntryRepository.save(entry);
        patient.get().addDiaryEntry(entry);
        patientRepository.save(patient.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path="/sleepEntries/{username}")
    public @ResponseBody Iterable<SleepEntry> getAllSleepEntriesByUser(Authentication authentication,
                                                                  @PathVariable("username") String username) {
        if (authentication.getName().equals(username)){
            Optional<Patient> patient = patientRepository.findByUsername(username);
            if (patient.isPresent()){
                return patient.get().getDiaryEntries().stream().map(DiaryEntry::getSleepEntry)
                        .collect(Collectors.toList());
            }
        }
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (doctor.isPresent() && doctor.get().getPatients().stream().anyMatch(x -> x.getUsername().equals(username))){
            Optional<Patient> patient = patientRepository.findByUsername(username);
            if (patient.isPresent()){
                return patient.get().getDiaryEntries().stream().map(DiaryEntry::getSleepEntry)
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

    @GetMapping(path = "/{username}/{id}")
    public @ResponseBody DiaryEntry getDiaryEntryById(Authentication authentication,
                                                      @PathVariable("username") String username,
                                                      @PathVariable("id") Long id){
        if (authentication.getName().equals(username)){
            Optional<Patient> patient = patientRepository.findByUsername(username);
            if (patient.isPresent()){
                Set<DiaryEntry> diaryEntryList = patient.get().getDiaryEntries();
                Optional<DiaryEntry> diaryEntry = diaryEntryList.stream().filter(x -> x.getId().equals(id)).findFirst();
                return diaryEntry.orElse(null);
            }
        }
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if(doctor.isPresent() && doctor.get().getPatients().stream().anyMatch(x -> x.getUsername().equals(username))){
            Optional<Patient> patient = patientRepository.findByUsername(username);
            if (patient.isPresent()){
                Set<DiaryEntry> diaryEntryList = patient.get().getDiaryEntries();
                Optional<DiaryEntry> diaryEntry = diaryEntryList.stream().filter(x -> x.getId().equals(id)).findFirst();
                return diaryEntry.orElse(null);
            }
        }
        return null;
    }
}
