package org.atlas.neuroguard.service.equipes;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.dto.EquipeDTO;

import java.util.Optional;
import java.util.UUID;

public interface IEquipeService {
    Optional<Equipe> findById(UUID uuid);
    Optional<Equipe> findByUser_Id(UUID uuid);
    EquipeDTO create(EquipeDTO dto);
    EquipeDTO update(UUID uuid, EquipeDTO dto);
    void delete(UUID uuid, UUID userId);

}
