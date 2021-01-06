package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.resource.models.Medicine;
import com.edu.agh.easist.easistserver.resource.models.MedicineEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicineEntryRepository extends CrudRepository<MedicineEntry, Long> {
    @Override
    <S extends MedicineEntry> S save(S s);

    List<MedicineEntry> findByMedicine(Medicine medicine);
}
