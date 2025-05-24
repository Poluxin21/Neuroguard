package org.atlas.neuroguard.security.service.user;

import org.atlas.neuroguard.infra.controllerUtils.CrudService;
import org.atlas.neuroguard.security.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserService extends CrudService<User, UUID> {
}

