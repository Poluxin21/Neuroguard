package org.atlas.neuroguard.infra.security.controller.user;

import org.atlas.neuroguard.infra.config.ApiErrorResponse;
import org.atlas.neuroguard.infra.config.SuccessResponse;
import org.atlas.neuroguard.infra.security.controller.user.DTOs.PasswordChangeRequest;
import org.atlas.neuroguard.infra.security.domain.user.User;
import org.atlas.neuroguard.infra.security.domain.user.dto.UserDTO;
import org.atlas.neuroguard.infra.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        try {
            Optional<UserDTO> user = userService.findByIdSecure(id);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("Erro interno do servidor"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            UserDTO createdUser = userService.createSecure(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("Erro interno do servidor"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody User user) {
        try {
            UserDTO updatedUser = userService.updateSecure(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("Erro interno do servidor"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception e for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("Erro interno do servidor"));
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable UUID id,
            @RequestBody PasswordChangeRequest request) {
        try {
            boolean success = userService.changePassword(id, request.currentPassword, request.newPassword);
            if (success) {
                return ResponseEntity.ok(new SuccessResponse("Senha alterada com sucesso"));
            } else {
                return ResponseEntity.badRequest().body(new ApiErrorResponse("Falha ao alterar senha"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception e for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("Erro interno do servidor"));
        }
    }
}