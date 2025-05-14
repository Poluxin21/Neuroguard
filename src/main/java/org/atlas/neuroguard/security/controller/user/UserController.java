package org.atlas.neuroguard.security.controller.user;

import org.atlas.neuroguard.infra.controllerutils.GenericRestController;
import org.atlas.neuroguard.security.domain.user.User;
import org.atlas.neuroguard.security.service.user.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController extends GenericRestController<User, UUID> {

    public UserController(IUserService service) {
        super(service);
    }
}