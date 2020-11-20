package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.resource.models.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);

    @Override
    <S extends Doctor> S save(S s);
}