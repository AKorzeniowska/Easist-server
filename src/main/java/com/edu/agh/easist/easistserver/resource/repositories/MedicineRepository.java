package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.resource.models.Medicine;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    @Override
    <S extends Medicine> S save(S s);

    @Override
    Optional<Medicine> findById(Long aLong);
}
