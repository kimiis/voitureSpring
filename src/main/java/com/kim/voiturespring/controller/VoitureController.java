package com.kim.voiturespring.controller;

import com.kim.voiturespring.dao.VoitureDao;
import com.kim.voiturespring.dto.VoitureDto;
import com.kim.voiturespring.service.VoitureService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carSpring")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    // Appel au service pour récupérer toutes les voitures
    @GetMapping
    public ResponseEntity<List<VoitureDao>> getAllVoitures() {
        return ResponseEntity.ok(voitureService.getAllVoitures());
    }

    // Appel au service pour récupérer une voiture
    @GetMapping("/{id}")
    public ResponseEntity<VoitureDao> getVoitureById(@PathVariable UUID id) {
        return ResponseEntity.ok(voitureService.getVoitureById(id));
    }

    // Appel au service pour créer une voiture
    @PostMapping
    public ResponseEntity<VoitureDao> createVoiture(@RequestBody VoitureDto voitureDto) {
        return ResponseEntity.ok(voitureService.createVoiture(voitureDto));
    }

    // Appel au service pour update une voiture
    @PutMapping("/{id}")
    public ResponseEntity<VoitureDao> updateVoiture(@PathVariable UUID id, @RequestBody VoitureDto voitureDto) {
        return ResponseEntity.ok(voitureService.updateVoiture(id, voitureDto));
    }

    // Appel au service pour update partiellement une voiture
    @PatchMapping("/{id}")
    public ResponseEntity<VoitureDao> patchVoiture(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        VoitureDao updatedVoiture = null;
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            updatedVoiture = voitureService.patchVoiture(id, entry.getKey(), entry.getValue());
        }
        return ResponseEntity.ok(updatedVoiture);
    }

    // Appel au service pour supprimer une voiture
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoiture(@PathVariable UUID id) {
        voitureService.deleteVoiture(id);
        return ResponseEntity.ok("Voiture supprimée avec succès !");
    }

}
