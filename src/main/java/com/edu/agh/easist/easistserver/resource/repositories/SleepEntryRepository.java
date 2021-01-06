package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.resource.models.SleepEntry;
import com.edu.agh.easist.easistserver.resource.models.Symptom;
import org.springframework.data.repository.CrudRepository;

public interface SleepEntryRepository extends CrudRepository<SleepEntry, Long> {
    @Override
    <S extends SleepEntry> S save(S s);
}
