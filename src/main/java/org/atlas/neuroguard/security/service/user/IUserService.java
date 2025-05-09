package org.atlas.neuroguard.security.service.user;

import org.atlas.neuroguard.security.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    User createUser(User user);
    Optional<User> getUserById(UUID userId);
    User updateUser(UUID userId, User user);
    void deleteUser(UUID userId);

}
