package org.atlas.neuroguard.infra.security.domain.user.dto;

import org.atlas.neuroguard.infra.security.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.tipoUsuario = user.getTipoUsuario();
        dto.tipoPermissao = user.getTipoPermissao();
        return dto;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
