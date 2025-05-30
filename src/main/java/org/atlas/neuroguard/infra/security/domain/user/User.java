package org.atlas.neuroguard.infra.security.domain.user;


import jakarta.persistence.*;
import org.atlas.neuroguard.infra.security.domain.user.enums.TipoPermissao;
import org.atlas.neuroguard.infra.security.domain.user.enums.TipoUsuario;

import java.util.UUID;

@Entity
@Table(schema = "appuser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String nome;
    public String sobrenome;
    public String username;
    public String password;
    public String email;
    public TipoUsuario tipoUsuario;
    public TipoPermissao tipoPermissao;
    public boolean ativo;

    public User() {}

    public User(
            String nome,
            String sobrenome,
            String username,
            String password,
            String email,
            TipoPermissao tipoPermissao,
            TipoUsuario tipoUsuario,
            boolean ativo)
    {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.username = username;
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

    public String getEmail()
    {
        return this.email;
    }

    public String getUsername()
    {
        return this.username;
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
