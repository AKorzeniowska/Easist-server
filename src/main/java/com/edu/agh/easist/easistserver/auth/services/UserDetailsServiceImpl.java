package com.edu.agh.easist.easistserver.auth.services;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.auth.repositories.UserRepository;
import com.edu.agh.easist.easistserver.auth.models.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(s);
        }
        return new UserPrincipal(user.get());
    }
}
