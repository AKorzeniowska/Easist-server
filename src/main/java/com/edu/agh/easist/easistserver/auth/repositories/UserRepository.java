package com.edu.agh.easist.easistserver.auth.repositories;

import com.edu.agh.easist.easistserver.auth.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String name);
    @Override
    <S extends User> S save(S s);
}
