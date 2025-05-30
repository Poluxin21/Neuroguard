package org.atlas.neuroguard.infra.security.domain.user.dto;

import org.atlas.neuroguard.infra.security.domain.user.enums.TipoPermissao;
import org.atlas.neuroguard.infra.security.domain.user.enums.TipoUsuario;

public class UserDTO {
    public String username;
    public String email;
    public TipoUsuario tipoUsuario;
    public TipoPermissao tipoPermissao;
}
