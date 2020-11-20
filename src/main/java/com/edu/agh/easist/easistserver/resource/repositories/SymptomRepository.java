package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.auth.models.User;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.models.Symptom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SymptomRepository extends CrudRepository<Symptom, Long> {
    @Override
    <S extends Symptom> S save(S s);
}
