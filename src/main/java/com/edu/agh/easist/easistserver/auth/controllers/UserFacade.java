package com.edu.agh.easist.easistserver.auth.controllers;

import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFacade {
    @Autowired
    private UserRepository userRepository;

}
