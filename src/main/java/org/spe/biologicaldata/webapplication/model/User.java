package org.spe.biologicaldata.webapplication.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity @Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") @Getter @Setter
    private Long id;

    @Column(name = "username") @Getter @Setter
    private String username;

    @Column(name = "password") @Getter @Setter
    private String password;

    @Transient @Column(name = "passwordConfirm") @Getter @Setter
    private String passwordConfirm;

    @ManyToMany @Getter @Setter
    private Set<Role> roles;

    public User() {
        username = "";
        password = "";
        passwordConfirm = "";
    }

    public User(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

}
