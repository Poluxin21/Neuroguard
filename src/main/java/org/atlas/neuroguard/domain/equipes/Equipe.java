package org.atlas.neuroguard.domain.equipes;


import jakarta.persistence.*;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    public UUID id;

    public String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipeManagerId")
    private User user;

    public Equipe(String codigo, User user) {
        this.codigo = codigo;
        this.user = user;
    }

    public Equipe() {}

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setUser(User user) {
        return;
    }

    public void setCodigo(String codigo) {
        return;
    }


}
