package com.edu.agh.easist.easistserver.resource.controllers;

import com.edu.agh.easist.easistserver.resource.data.DiaryEntryData;
import com.edu.agh.easist.easistserver.resource.data.InvitationData;
import com.edu.agh.easist.easistserver.resource.data.MedicineEntryData;
import com.edu.agh.easist.easistserver.resource.data.SymptomEntryData;
import com.edu.agh.easist.easistserver.resource.models.*;
import com.edu.agh.easist.easistserver.resource.repositories.DoctorRepository;
import com.edu.agh.easist.easistserver.resource.repositories.InvitationRepository;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Validated
@RequestMapping(path="/invitation")
public class InvitationController {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    @PostMapping(path="")
    public ResponseEntity<Void> addInvitation(Authentication authentication, @RequestBody InvitationData invitationData){
        Optional<Doctor> doctor = doctorRepository.findByUsername(invitationData.getDoctorUsername());
        Optional<Patient> patient = patientRepository.findById(invitationData.getPatientId());
        if (doctor.isEmpty() || patient.isEmpty() || !authentication.getName().equals(invitationData.getDoctorUsername()))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Invitation invitation = new Invitation(invitationData);
        invitation.setDoctor(doctor.get());
        invitation.setPatient(patient.get());

        invitationRepository.save(invitation);
        patientRepository.save(patient.get());
        doctorRepository.save(doctor.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping(path="")
    public ResponseEntity<Void> updateInvitation(Authentication authentication, @RequestBody InvitationData invitationData) {
        Optional<Doctor> doctor = doctorRepository.findById(invitationData.getDoctorId());
        Optional<Patient> patient = patientRepository.findById(invitationData.getPatientId());
        Optional<Invitation> invitation = invitationRepository.findById(invitationData.getId());
        if (doctor.isEmpty() || patient.isEmpty() ||
                !authentication.getName().equals(invitationData.getPatientUsername()) || invitation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        invitation.get().setIsActive(invitationData.getIsActive());
        invitation.get().setGotAccepted(invitationData.getGotAccepted());

        if (invitationData.getGotAccepted()){
            doctor.get().addPatient(patient.get());
            patient.get().setDoctor(doctor.get());
        }

        invitationRepository.save(invitation.get());
        patientRepository.save(patient.get());
        doctorRepository.save(doctor.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/patients")
    @ResponseBody
    public Iterable<Patient> getSentInvitations(Authentication authentication){
        Optional<Doctor> doctor = doctorRepository.findByUsername(authentication.getName());
        if (doctor.isEmpty() || !doctor.get().getUsername().equals(authentication.getName()))
            return null;
        List<Invitation> invitations = invitationRepository.findByDoctorId(doctor.get().getId());
        return invitations.stream().filter(Invitation::getIsActive)
                .map(Invitation::getPatient).collect(Collectors.toList());
    }

    @GetMapping(value = "/doctors")
    @ResponseBody
    public Iterable<Doctor> getReceivedInvitations(Authentication authentication){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isEmpty() || !patient.get().getUsername().equals(authentication.getName()))
            return null;
        List<Invitation> invitations = invitationRepository.findByPatientId(patient.get().getId());
        return invitations.stream().filter(Invitation::getIsActive)
                .map(Invitation::getDoctor).collect(Collectors.toList());
    }

    @GetMapping(value = "/active")
    @ResponseBody
    public Invitation getActiveInvitation(Authentication authentication){
        Optional<Patient> patient = patientRepository.findByUsername(authentication.getName());
        if (patient.isEmpty() || !patient.get().getUsername().equals(authentication.getName()))
            return null;
        List<Invitation> invitations = invitationRepository.findByPatientId(patient.get().getId());
        return invitations.get(0);
    }
}
