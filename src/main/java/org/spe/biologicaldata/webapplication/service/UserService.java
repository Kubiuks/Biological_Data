package org.spe.biologicaldata.webapplication.service;

import org.spe.biologicaldata.webapplication.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}