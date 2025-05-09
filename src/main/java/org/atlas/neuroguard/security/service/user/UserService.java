package org.atlas.neuroguard.security.service.user;

import org.atlas.neuroguard.security.domain.user.User;
import org.atlas.neuroguard.security.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        Optional<User> email = userRepository.findByEmail(user.email);
        if (email.isPresent())
            throw new IllegalArgumentException("Email já está em uso.");

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        Optional<User> userQuery = userRepository.findById(userId);
        if(!userQuery.isPresent())
            throw new RuntimeException("User not found");

        return userQuery;
    }

    @Override
    public User updateUser(UUID userId, User user) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
