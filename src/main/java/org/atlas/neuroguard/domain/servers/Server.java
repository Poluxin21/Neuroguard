package org.atlas.neuroguard.domain.servers;

import jakarta.persistence.*;
import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Server {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    public UUID id;

    public String codigo;
    public String ip;
    public Integer porta;
    public String login;
    public String senha;

    @Enumerated(EnumType.STRING)
    public TipoServidor tipoServidor;

    @ManyToOne
    @JoinColumn(name = "responsavelId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "equipeId")
    private Equipe equipe;

    public Server() {
    }

    public UUID getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoServidor getTipoServidor() {
        return tipoServidor;
    }

    public void setTipoServidor(TipoServidor tipoServidor) {
        this.tipoServidor = tipoServidor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
