package com.edu.agh.easist.easistserver.repositories;

import com.edu.agh.easist.easistserver.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long aLong);
    Optional<Role> findByRoleName(String roleName);
}
