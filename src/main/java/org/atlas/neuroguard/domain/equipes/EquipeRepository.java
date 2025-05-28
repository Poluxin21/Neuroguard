package org.atlas.neuroguard.domain.equipes;

import org.atlas.neuroguard.infra.security.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EquipeRepository extends JpaRepository<Equipe, UUID> {
    Optional<Equipe> findByCodigoAndUser_Id(String codigo, UUID userId);
    Optional<Equipe> findById(UUID id);
    Optional<Equipe> findByUser(User user);

}
