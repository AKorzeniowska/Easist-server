package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.resource.models.Patient;
import com.edu.agh.easist.easistserver.resource.models.Symptom;
import com.edu.agh.easist.easistserver.resource.models.SymptomEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SymptomEntryRepository extends CrudRepository<SymptomEntry, Long> {
    @Override
    <S extends SymptomEntry> S save(S s);

    List<SymptomEntry> findBySymptom(Symptom symptom);
}
