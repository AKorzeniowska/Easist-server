package com.edu.agh.easist.easistserver.services;

import com.edu.agh.easist.easistserver.models.User;
import com.edu.agh.easist.easistserver.models.UserPrincipal;
import com.edu.agh.easist.easistserver.repositories.UserRepository;
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
        Optional<User> user = userRepository.findByName(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(s);
        }
        return new UserPrincipal(user.get());
    }
}
