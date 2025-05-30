package org.atlas.neuroguard.infra.security.service.user;

import org.atlas.neuroguard.infra.controllerUtils.CrudService;
import org.atlas.neuroguard.infra.security.domain.user.User;

import java.util.UUID;

public interface IUserService extends CrudService<User, UUID> {
}

