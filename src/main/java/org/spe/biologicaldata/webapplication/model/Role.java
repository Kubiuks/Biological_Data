package org.spe.biologicaldata.webapplication.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity @Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") @Getter @Setter
    private Long id;

    @Column(name = "name") @Getter @Setter
    private String name;

    @ManyToMany(mappedBy = "roles") @Getter @Setter
    private Set<User> users;

    public Role() {
        name = "";
    }

    public Role(String name) {
        this.name = name;
    }

}
