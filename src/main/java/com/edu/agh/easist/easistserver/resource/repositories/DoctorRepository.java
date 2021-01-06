package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Optional<Doctor> findByUsername(String username);

    @Override
    <S extends Doctor> S save(S s);
}