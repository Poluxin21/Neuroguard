package org.atlas.neuroguard.infra.security.controller.token;

import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.service.jwt.JwtService;
import org.atlas.neuroguard.infra.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/token")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(JwtService jwtService, UserService userService)
    {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping
    public String login(@RequestParam String username) {
        HashMap<String, Object> claims = new HashMap<>();

        User user = userService.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("tipoUsuario", user.getTipoUsuario().name());
        claims.put("tipoPermissao", user.getTipoPermissao().name());
        claims.put("ativo", user.isAtivo());

        return jwtService.generateToken(username, claims);
    }
}