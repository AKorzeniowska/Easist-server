package com.edu.agh.easist.easistserver.resource.repositories;

import com.edu.agh.easist.easistserver.resource.models.Doctor;
import com.edu.agh.easist.easistserver.resource.models.Invitation;
import com.edu.agh.easist.easistserver.resource.models.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    @Override
    <S extends Invitation> S save(S s);

    @Override
    Optional<Invitation> findById(Long aLong);

    List<Invitation> findByDoctorId(Long doctorId);
    List<Invitation> findByPatientId(Long patientId);
}
