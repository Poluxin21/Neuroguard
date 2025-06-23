package org.atlas.neuroguard.controller.equipes;

import org.atlas.neuroguard.domain.equipes.Equipe;
import org.atlas.neuroguard.domain.equipes.dto.EquipeDTO;
import org.atlas.neuroguard.service.equipes.EquipeService;
import org.atlas.neuroguard.service.equipes.IEquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private final IEquipeService equipeService;

    @Autowired
    public EquipeController(IEquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @GetMapping
    public Optional<Equipe> getEquipeByUser(@RequestParam UUID userId) {
        return equipeService.findByUser_Id(userId);
    }

    @GetMapping("/{uuid}")
    public Optional<Equipe> getEquipe(@PathVariable UUID uuid) {
        return equipeService.findById(uuid);
    }

    @PostMapping
    public EquipeDTO createEquipe(@RequestBody EquipeDTO dto) {
        return equipeService.create(dto);
    }

    @PutMapping("/{uuid}")
    public EquipeDTO updateEquipe(@PathVariable UUID uuid, @RequestBody EquipeDTO dto) {
        return equipeService.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteEquipe(@PathVariable UUID uuid, @RequestParam UUID userId) {
        equipeService.delete(uuid, userId);
    }
}
