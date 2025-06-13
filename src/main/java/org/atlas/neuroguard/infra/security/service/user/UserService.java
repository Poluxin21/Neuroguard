package org.atlas.neuroguard.infra.security.service.user;

import jakarta.persistence.EntityNotFoundException;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.domain.user.UserRepository;
import org.atlas.neuroguard.infra.security.domain.user.dto.UserDTO;
import org.atlas.neuroguard.infra.security.domain.user.dto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo");
        }
        return userRepository.findById(uuid);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findByIdSecure(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo");
        }

        return userRepository.findById(uuid)
                .map(UserMapper::toDTO);
    }

    @Override
    public User create(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entidade User não pode ser nula");
        }

        validateUserData(entity);

        // Criptografar senha antes de salvar
        if (entity.password != null && !entity.password.isEmpty()) {
            entity.password = passwordEncoder.encode(entity.password);
        }

        var userQuery = userRepository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail());

        if (userQuery.isPresent()) {
            var existingUser = userQuery.get();

            if (existingUser.getEmail().equals(entity.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso");
            }

            if (existingUser.getUsername().equals(entity.getUsername())) {
                throw new IllegalArgumentException("Username já está em uso");
            }
        }

        try {
            return userRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro de integridade de dados ao criar usuário", e);
        }
    }

    // Método seguro para criação que retorna DTO
    public UserDTO createSecure(User entity) {
        User savedUser = create(entity);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public User update(UUID uuid, User entity) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo");
        }

        if (entity == null) {
            throw new IllegalArgumentException("Entidade User não pode ser nula");
        }

        User existingUser = userRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + uuid));

        validateUserData(entity);

        // Verificar se email/username já existem para outros usuários
        var conflictUserQuery = userRepository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail());
        if (conflictUserQuery.isPresent()) {
            var conflictUser = conflictUserQuery.get();

            // Se não for o mesmo usuário sendo atualizado
            if (!conflictUser.getId().equals(uuid)) {
                if (conflictUser.getEmail().equals(entity.getEmail())) {
                    throw new IllegalArgumentException("Email já está em uso por outro usuário");
                }

                if (conflictUser.getUsername().equals(entity.getUsername())) {
                    throw new IllegalArgumentException("Username já está em uso por outro usuário");
                }
            }
        }

        // Manter a senha existente se não foi fornecida nova senha
        if (entity.password == null || entity.password.isEmpty()) {
            entity.password = existingUser.password;
        } else {
            entity.password = passwordEncoder.encode(entity.password);
        }

        entity.id = uuid;

        try {
            return userRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro de integridade de dados ao atualizar usuário", e);
        }
    }

    // Método seguro para atualização que retorna DTO
    public UserDTO updateSecure(UUID uuid, User entity) {
        User updatedUser = update(uuid, entity);
        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo");
        }

        if (!userRepository.existsById(uuid)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + uuid);
        }

        userRepository.deleteById(uuid);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        return userRepository.findByEmail(email);
    }

    // Método seguro que retorna DTO
    @Transactional(readOnly = true)
    public Optional<UserDTO> findByEmailSecure(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username não pode ser nulo ou vazio");
        }
        return userRepository.findByUsername(username);
    }

    // Método seguro que retorna DTO
    @Transactional(readOnly = true)
    public Optional<UserDTO> findByUsernameSecure(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username não pode ser nulo ou vazio");
        }

        return userRepository.findByUsername(username)
                .map(UserMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Método seguro que retorna lista de DTOs
    @Transactional(readOnly = true)
    public List<UserDTO> findAllSecure() {
        List<User> users = userRepository.findAll();
        return UserMapper.toDTOList(users);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findByTipoUsuarioSecure(org.atlas.neuroguard.infra.security.domain.user.enums.TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo");
        }

        List<User> users = userRepository.findByTipoUsuario(tipoUsuario);
        return UserMapper.toDTOList(users);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findActiveUsersSecure() {
        List<User> users = userRepository.findByAtivoTrue();
        return UserMapper.toDTOList(users);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByUsername(username);
    }

    public boolean changePassword(UUID userId, String currentPassword, String newPassword) {
        if (userId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        if (currentPassword == null || newPassword == null) {
            throw new IllegalArgumentException("Senhas não podem ser nulas");
        }

        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Nova senha deve ter pelo menos 8 caracteres");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Verificar senha atual
        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        // Atualizar senha
        user.password = passwordEncoder.encode(newPassword);
        userRepository.save(user);

        return true;
    }


    private void validateUserData(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username não pode ser nulo ou vazio");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        // Validação básica de email
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }

        // Validação de tamanho do username
        if (user.getUsername().length() < 3 || user.getUsername().length() > 50) {
            throw new IllegalArgumentException("Username deve ter entre 3 e 50 caracteres");
        }

        if (user.nome == null || user.nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        if (user.sobrenome == null || user.sobrenome.trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome não pode ser nulo ou vazio");
        }

        if (user.getTipoUsuario() == null) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo");
        }

        if (user.getTipoPermissao() == null) {
            throw new IllegalArgumentException("Tipo de permissão não pode ser nulo");
        }
    }
}