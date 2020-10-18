package com.edu.agh.easist.easistserver.data.repositories;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.data.models.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);

    @Override
    <S extends Patient> S save(S s);
}
