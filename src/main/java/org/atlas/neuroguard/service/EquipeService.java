package org.atlas.neuroguard.service;

import jakarta.persistence.EntityNotFoundException;
import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.EquipeRepository;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EquipeService implements IEquipeService{

    private final EquipeRepository repository;

    @Autowired
    public EquipeService(EquipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Equipe> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Optional<Equipe> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Equipe create(Equipe entity) {
        var equipeQuery = repository.findByCodigoAndUser_Id(entity.codigo, entity.getUser().id)
                .orElseThrow(() -> new IllegalArgumentException("O Codigo de equipe ja existe"));

        return repository.save(entity);
    }

    @Override
    public Equipe update(UUID uuid, Equipe entity) {
        var existsEquipe = repository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Não foi possivel encontrar a equipe"));

        if(existsEquipe.getUser().id != entity.getUser().id)
            throw new IllegalArgumentException("Apenas o proprietario da equipe pode atualizar");

        return repository.save(entity);
    }


    public void delete(UUID uuid, UUID userId) {
        var existsEquipe = repository.findById(uuid);
        if (existsEquipe.isEmpty())
            throw new EntityNotFoundException("Não foi possivel encontrar a equipe");

        if(existsEquipe.get().getUser().id == userId)
            repository.deleteById(uuid);
        else throw new IllegalArgumentException("Usuário não é proprietario da equipe");

    }
}
