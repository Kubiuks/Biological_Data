package org.spe.biologicaldata.webapplication.repository;

import org.spe.biologicaldata.webapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}