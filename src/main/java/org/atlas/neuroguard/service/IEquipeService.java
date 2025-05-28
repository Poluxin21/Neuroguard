package org.atlas.neuroguard.service;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.infra.controllerUtils.CrudService;
import org.atlas.neuroguard.infra.security.domain.user.User;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface IEquipeService {
    Optional<Equipe> findById(UUID uuid);
    Optional<Equipe> findByUser(User user);
    Equipe create(Equipe entity);
    Equipe update(UUID uuid, Equipe entity);
    void delete(UUID uuid, UUID userId);

}
