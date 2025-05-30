package org.atlas.neuroguard.infra.security.domain.user.dto;

import org.atlas.neuroguard.infra.security.domain.user.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.tipoUsuario = user.getTipoUsuario();
        dto.tipoPermissao = user.getTipoPermissao();
        return dto;
    }
}
