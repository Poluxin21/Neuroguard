package org.atlas.neuroguard.controller.servers;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.dto.EquipeDTO;
import org.atlas.neuroguard.domain.servers.Server;
import org.atlas.neuroguard.domain.servers.dto.ServerDTO;
import org.atlas.neuroguard.service.equipes.EquipeService;
import org.atlas.neuroguard.service.servers.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final IServerService serverService;

    @Autowired
    public ServerController(IServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public Optional<Server> getServerByUser(@RequestParam UUID userId) {
        return serverService.findByUser_Id(userId);
    }

    @GetMapping("/{uuid}")
    public Optional<Server> getServer(@PathVariable UUID uuid) {
        return serverService.findById(uuid);
    }

    @PostMapping
    public ServerDTO createServer(@RequestBody ServerDTO dto) {
        return serverService.create(dto);
    }

    @PutMapping("/{uuid}")
    public ServerDTO updateServer(@PathVariable UUID uuid, @RequestBody ServerDTO dto) {
        return serverService.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteServer(@PathVariable UUID uuid, @RequestParam UUID userId) {
        serverService.delete(uuid, userId);
    }
}