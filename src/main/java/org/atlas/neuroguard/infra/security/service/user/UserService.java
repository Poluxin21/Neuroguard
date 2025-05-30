package org.atlas.neuroguard.infra.security.service.user;

import jakarta.persistence.EntityNotFoundException;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public User create(User entity) {
        var userQuery = userRepository.findByUsernameOrEmail(entity.username, entity.email);

        if (userQuery.isPresent()) {
            var existingUser = userQuery.get();

            if(existingUser.getEmail().equals(entity.getEmail()))
                throw new IllegalArgumentException("Email já está em uso");

            if(existingUser.getUsername().equals(entity.getUsername()))
                throw new IllegalArgumentException("Username já está em uso");
        }

        return userRepository.save(entity);
    }

    @Override
    public User update(UUID uuid, User entity) {
        if (userRepository.existsById(uuid)) {
            entity.id = uuid;
            return userRepository.save(entity);
        }
        throw new EntityNotFoundException("Usuário não encontrado");
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    public Optional<User> findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }
}