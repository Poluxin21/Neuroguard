package org.atlas.neuroguard.domain.servers.dto;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.servers.Server;
import org.atlas.neuroguard.domain.servers.TipoServidor;
import org.atlas.neuroguard.infra.security.domain.user.User;

import java.util.UUID;

public class ServerMapper {

    public static Server toEntity(ServerDTO dto, User user, Equipe equipe) {
        Server server = new Server();
        server.setCodigo(dto.codigo);
        server.setIp(dto.ip);
        server.setPorta(dto.porta);
        server.setLogin(dto.login);
        server.setSenha(dto.senha);
        server.setTipoServidor(dto.tipoServidor);
        server.setUser(user);
        server.setEquipe(equipe);
        return server;
    }

    public static ServerDTO toDTO(Server server) {
        return new ServerDTO(
                server.getId(),
                server.getUser() != null ? server.getUser().getId() : null,
                server.getEquipe() != null ? server.getEquipe().getId() : null,
                server.getCodigo(),
                server.getIp(),
                server.getPorta(),
                server.getLogin(),
                server.getSenha(),
                server.getTipoServidor()
        );
    }
}