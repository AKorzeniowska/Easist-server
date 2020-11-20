package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
    Optional<Patient> findByUsername(String username);

    @Override
    <S extends Patient> S save(S s);
}
