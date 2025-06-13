package org.atlas.neuroguard.service.equipes;

import jakarta.persistence.EntityNotFoundException;
import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.EquipeRepository;
import org.atlas.neuroguard.domain.equipes.dto.EquipeDTO;
import org.atlas.neuroguard.domain.equipes.dto.EquipeMapper;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EquipeService implements IEquipeService {

    private final EquipeRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public EquipeService(EquipeRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Equipe> findByUser_Id(UUID uuid) {
        return repository.findByUser_Id(uuid);
    }

    @Override
    public Optional<Equipe> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public EquipeDTO create(EquipeDTO dto) {
        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        repository.findByCodigoAndUser_Id(dto.codigo, dto.userId).ifPresent(e -> {
            throw new IllegalArgumentException("O código de equipe já existe");
        });

        Equipe equipe = EquipeMapper.toEntity(dto, user);
        return EquipeMapper.toDTO(repository.save(equipe));
    }

    @Override
    public EquipeDTO update(UUID uuid, EquipeDTO dto) {
        Equipe equipeExistente = repository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada"));

        if (!equipeExistente.getUser().getId().equals(dto.userId)) {
            throw new IllegalArgumentException("Apenas o proprietário da equipe pode atualizar");
        }

        equipeExistente.setCodigo(dto.codigo);
        return EquipeMapper.toDTO(repository.save(equipeExistente));
    }


    public void delete(UUID uuid, UUID userId) {
        var existsEquipe = repository.findById(uuid);
        if (existsEquipe.isEmpty())
            throw new EntityNotFoundException("Não foi possivel encontrar a equipe");

        if (existsEquipe.get().getUser().id == userId)
            repository.deleteById(uuid);
        else throw new IllegalArgumentException("Usuário não é proprietario da equipe");

    }
}
