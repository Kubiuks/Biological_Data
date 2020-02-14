package org.spe.biologicaldata.webapplication.repository;


import org.spe.biologicaldata.webapplication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}