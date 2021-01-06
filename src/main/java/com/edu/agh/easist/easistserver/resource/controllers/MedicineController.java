package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.resource.data.MedicineData;
import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.models.Medicine;
import com.edu.agh.easist.easistserver.resource.repositories.DoctorRepository;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
import com.edu.agh.easist.easistserver.resource.repositories.MedicineRepository;
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
@RequestMapping(path="/medicine")
public class MedicineController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    @PostMapping(path="")
    public ResponseEntity<Void> addMedicine(Authentication authentication, @RequestBody MedicineData medicineData){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Medicine medicine = new Medicine(medicineData);
        medicineRepository.save(medicine);
        patient.get().addMedicine(medicine);
        patientRepository.save(patient.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path="/{username}")
    public @ResponseBody
    Iterable<Medicine> getAllMedicinesByUser(Authentication authentication,
                                           @PathVariable("username") String username){
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        Optional<Patient> patient = patientRepository.findByUsername(username);
        if (patient.isEmpty())
            return null;
        if (patient.get().getUsername().equals(username) ||
                (doctor.isPresent() && doctor.get().getPatients().stream().anyMatch(x -> x.getUsername().equals(username))))
            return patient.get().getMedicines();
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Void> deleteMedicine(Authentication authentication, @PathVariable("id") Long id){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isPresent()){
            Optional<Medicine> medicine = patient.get().getMedicines().stream()
                    .filter(x -> x.getId().equals(id)).findFirst();
            if (medicine.isPresent()) {
                medicineRepository.delete(medicine.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
