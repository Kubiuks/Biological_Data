package org.spe.biologicaldata.webapplication.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}