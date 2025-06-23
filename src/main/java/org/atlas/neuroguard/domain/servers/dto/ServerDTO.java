package org.atlas.neuroguard.domain.servers.dto;

import org.atlas.neuroguard.domain.servers.Server;
import org.atlas.neuroguard.domain.servers.TipoServidor;

import java.util.UUID;

public class ServerDTO {
    public UUID id;
    public UUID userId;
    public UUID equipeId;
    public String codigo;
    public String ip;
    public Integer porta;
    public String login;
    public String senha;
    public TipoServidor tipoServidor;

    public ServerDTO() {
    }

    public ServerDTO(UUID id, UUID userId, UUID equipeId, String codigo, String ip, Integer porta,
                     String login, String senha, TipoServidor tipoServidor) {
        this.id = id;
        this.userId = userId;
        this.equipeId = equipeId;
        this.codigo = codigo;
        this.ip = ip;
        this.porta = porta;
        this.login = login;
        this.senha = senha;
        this.tipoServidor = tipoServidor;
    }
}