package org.atlas.neuroguard.domain.equipes.dto;

import org.atlas.neuroguard.domain.equipes.Equipe;

public class EquipeMapper {
    public static EquipeDTO toDTO(Equipe equipe) {
        EquipeDTO dto = new EquipeDTO();
        dto.codigo = equipe.getCodigo();
        dto.userId = equipe.getUser().getId();
        return dto;
    }
}
