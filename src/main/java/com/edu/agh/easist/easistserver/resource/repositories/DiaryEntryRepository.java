package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.resource.models.DiaryEntry;
import com.edu.agh.easist.easistserver.resource.models.Symptom;
import org.springframework.data.repository.CrudRepository;

public interface DiaryEntryRepository extends CrudRepository<DiaryEntry, Long> {
    @Override
    <S extends DiaryEntry> S save(S s);
}
