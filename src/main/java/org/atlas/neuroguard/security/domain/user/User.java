package org.atlas.neuroguard.security.domain.user;


import jakarta.persistence.*;
import org.atlas.neuroguard.security.domain.user.enums.TipoPermissao;
import org.atlas.neuroguard.security.domain.user.enums.TipoUsuario;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    public TipoUsuario tipoUsuario;
    public TipoPermissao tipoPermissao;
    public boolean ativo;

    public User() {}

    public User(
            String nome,
            String sobrenome,
            String password,
            String email,
            TipoPermissao tipoPermissao,
            TipoUsuario tipoUsuario,
            boolean ativo)
    {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.password = password;
        this.email = email;
        this.tipoPermissao = tipoPermissao;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;

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

    public TipoPermissao getTipoPermissao() {
        return tipoPermissao;
    }
    
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
