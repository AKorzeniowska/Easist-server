package com.edu.agh.easist.easistserver.auth.controllers;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.Role;
import com.edu.agh.easist.easistserver.auth.models.UserData;
import com.edu.agh.easist.easistserver.auth.repositories.RoleRepository;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.repositories.DoctorRepository;
import com.edu.agh.easist.easistserver.resource.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Validated
@RequestMapping(path="/auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(path="/role")
    public @ResponseBody String getRole(Authentication authentication){
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()){
            return null;
        } else if (user.get().getRoles().stream().map(Role::getRoleName).anyMatch("doctor"::equals)) {
            return "doctor";
        } else {
            return "patient";
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path="/register")
    public ResponseEntity<Void> register(@RequestBody UserData userData){
        Optional<User> existingUser = userRepository.findByUsername(userData.getUsername());
        if (existingUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Role> role = roleRepository.findByRoleName(userData.getRole());

        if (role.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = new User(userData.getUsername(), hash(userData.getPassword()));
        role.ifPresent(user::addRole);
        userRepository.save(user);

        saveUser(userData, role.get(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: handle log_rounds randomization?
    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    ///TODO: factory?
    private void saveUser(UserData userData, Role role, User user){
        //TODO: get out the configuration
        if (role != null && role.getRoleName().toLowerCase().equals("patient")) {
            Patient patient = new Patient(userData);
            patientRepository.save(patient);
        } else if (role != null && role.getRoleName().toLowerCase().equals("doctor")) {
            Doctor doctor = new Doctor(userData);
            doctorRepository.save(doctor);
        }
    }
}