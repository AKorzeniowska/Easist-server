package com.edu.agh.easist.easistserver.auth.controllers;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.models.Role;
import com.edu.agh.easist.easistserver.auth.repositories.RoleRepository;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.data.models.Patient;
import com.edu.agh.easist.easistserver.data.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
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

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path="/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Void> register(@RequestParam String username, @RequestParam String password, @RequestParam String roleName){
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = new User(username, hash(password));
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        role.ifPresent(user::addRole);
        userRepository.save(user);
        //TODO: get tf out the configuration
        if (role.isPresent() && role.get().getRoleName().toLowerCase().equals("patient")) {
            Patient patient = new Patient("testname", "testlast", "test@test.com", "+48516055646");
            patient.setUser(user);
            patientRepository.save(patient);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: handle log_rounds randomization?
    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}