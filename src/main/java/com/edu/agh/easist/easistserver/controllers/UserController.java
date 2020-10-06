package com.edu.agh.easist.easistserver.controllers;

import com.edu.agh.easist.easistserver.models.Role;
import com.edu.agh.easist.easistserver.models.User;
import com.edu.agh.easist.easistserver.repositories.RoleRepository;
import com.edu.agh.easist.easistserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Validated
@RequestMapping(path="/demo")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {

        User n = new User("test1", "mail@mail.com", "pass1");
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path="/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String roleName){
        User user = new User(username, email, hash(password));
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        role.ifPresent(user::addRole);
        userRepository.save(user);
        return new ResponseEntity<>("user created", HttpStatus.OK);
    }

    //TODO: handle log_rounds randomization?
    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}