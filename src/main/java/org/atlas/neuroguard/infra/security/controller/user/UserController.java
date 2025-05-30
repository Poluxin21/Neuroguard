package org.atlas.neuroguard.infra.security.controller.user;

import org.atlas.neuroguard.infra.controllerUtils.GenericRestController;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.service.user.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends GenericRestController<User, UUID> {

    public UserController(IUserService service) {
        super(service);
    }
}