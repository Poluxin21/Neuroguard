package org.atlas.neuroguard.service.servers;


import jakarta.persistence.EntityNotFoundException;
import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.EquipeRepository;
import org.atlas.neuroguard.domain.servers.Server;
import org.atlas.neuroguard.domain.servers.ServerRepository;
import org.atlas.neuroguard.domain.servers.dto.ServerDTO;
import org.atlas.neuroguard.domain.servers.dto.ServerMapper;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ServerService implements IServerService {

    private final ServerRepository repository;
    private final UserRepository userRepository;
    private final EquipeRepository equipeRepository;

    @Autowired
    public ServerService(ServerRepository repository, UserRepository userRepository, EquipeRepository equipeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.equipeRepository = equipeRepository;
    }

    @Override
    public Optional<Server> findByUser_Id(UUID userId) {
        return repository.findByUser_Id(userId);
    }

    @Override
    public Optional<Server> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public ServerDTO create(ServerDTO dto) {
        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Equipe equipe = null;
        if (dto.equipeId != null) {
            equipe = equipeRepository.findById(dto.equipeId)
                    .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada"));
        }

        repository.findByCodigoAndUser_Id(dto.codigo, dto.userId).ifPresent(s -> {
            throw new IllegalArgumentException("Já existe um servidor com esse código para este usuário");
        });

        Server server = ServerMapper.toEntity(dto, user, equipe);
        return ServerMapper.toDTO(repository.save(server));
    }

    @Override
    public ServerDTO update(UUID id, ServerDTO dto) {
        Server existingServer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servidor não encontrado"));

        if (!existingServer.getUser().getId().equals(dto.userId)) {
            throw new IllegalArgumentException("Apenas o proprietário pode atualizar");
        }

        Equipe equipe = null;
        if (dto.equipeId != null) {
            equipe = equipeRepository.findById(dto.equipeId)
                    .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada"));
        }

        existingServer.setCodigo(dto.codigo);
        existingServer.setIp(dto.ip);
        existingServer.setPorta(dto.porta);
        existingServer.setLogin(dto.login);
        existingServer.setSenha(dto.senha);
        existingServer.setTipoServidor(dto.tipoServidor);
        existingServer.setEquipe(equipe);

        return ServerMapper.toDTO(repository.save(existingServer));
    }

    @Override
    public void delete(UUID id, UUID userId) {
        Server existingServer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servidor não encontrado"));

        if (!existingServer.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Usuário não é proprietário do servidor");
        }

        repository.deleteById(id);
    }
}
