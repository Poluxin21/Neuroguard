package org.atlas.neuroguard.service.servers;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.dto.EquipeDTO;
import org.atlas.neuroguard.domain.servers.Server;
import org.atlas.neuroguard.domain.servers.dto.ServerDTO;

import java.util.Optional;
import java.util.UUID;

public interface IServerService {
    Optional<Server> findById(UUID uuid);
    Optional<Server> findByUser_Id(UUID uuid);
    ServerDTO create(ServerDTO dto);
    ServerDTO update(UUID uuid, ServerDTO dto);
    void delete(UUID uuid, UUID userId);
}