package org.atlas.neuroguard.security.controller.token;

import org.atlas.neuroguard.security.service.jwt.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/token")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public String login(@RequestParam String username) {
        HashMap<String, Object> claims = new HashMap<>();
//        claims.put("role", "USER");

        return jwtService.generateToken(username, claims);
    }
}