package org.atlas.neuroguard.infra.security.domain.user;

import org.atlas.neuroguard.infra.security.domain.user.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    List<User> findByTipoUsuario(TipoUsuario tipoUsuario);
    List<User> findByAtivoTrue();
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
