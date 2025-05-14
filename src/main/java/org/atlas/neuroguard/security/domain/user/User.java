package org.atlas.neuroguard.security.domain.user;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "appuser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String nome;
    public String sobrenome;
    public String password;
    public String email;

    public User() {}

    public User(String nome, String sobrenome, String password, String email)
    {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.password = password;
        this.email = email;

    }

    public UUID getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.nome;
    }

    public String getSobrenome()
    {
        return this.sobrenome;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setName(String nome)
    {
        this.nome = nome;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

}
