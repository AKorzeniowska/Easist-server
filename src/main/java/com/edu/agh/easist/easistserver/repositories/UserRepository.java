package com.edu.agh.easist.easistserver.repositories;

import com.edu.agh.easist.easistserver.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
//    Optional<User> findById(Integer integer);
    Optional<User> findByName(String name);
}
