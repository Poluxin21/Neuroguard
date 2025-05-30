package org.atlas.neuroguard.domain.equipes.dto;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.infra.security.domain.user.User;

public class EquipeMapper {
    public static EquipeDTO toDTO(Equipe equipe) {
        EquipeDTO dto = new EquipeDTO();
        dto.codigo = equipe.getCodigo();
        dto.userId = equipe.getUser().getId();
        return dto;
    }

    public static Equipe toEntity(EquipeDTO dto, User user) {
        Equipe entity = new Equipe();
        entity.setCodigo(dto.codigo);
        entity.setUser(user);
        return entity;
    }
}
