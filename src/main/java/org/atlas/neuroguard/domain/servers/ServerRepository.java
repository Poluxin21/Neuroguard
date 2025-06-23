package org.atlas.neuroguard.domain.servers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    Optional<Server> findByUser_Id(UUID userId);
    Optional<Server> findByCodigoAndUser_Id(String codigo, UUID userId);
}