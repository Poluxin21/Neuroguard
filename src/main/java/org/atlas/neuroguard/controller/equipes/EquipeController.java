package org.atlas.neuroguard.controller.equipes;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.dto.EquipeDTO;
import org.atlas.neuroguard.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipes")
public class EquipeController {

    private final EquipeService equipeService;

    @Autowired
    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @GetMapping
    public Optional<Equipe> getEquipeByUser(@RequestParam UUID userId) {
        return equipeService.findByUser_Id(userId);
    }

    @GetMapping(value = "/{uuid}")
    public Optional<Equipe> getEquipe(@PathVariable UUID uuid) {
        return equipeService.findById(uuid);
    }

    @PostMapping
    public EquipeDTO createEquipe(@RequestBody EquipeDTO dto) {
        return equipeService.create(dto);
    }

    @PutMapping(value = "/{uuid}")
    public EquipeDTO updateEquipe(@PathVariable UUID uuid, @RequestBody EquipeDTO dto) {
        return equipeService.update(uuid, dto);
    }

    @DeleteMapping(value = "/{uuid}")
    public void deleteEquipe(@PathVariable UUID uuid, @RequestParam UUID userId) {
        equipeService.delete(uuid, userId);
    }
}
