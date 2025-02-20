package com.kim.voiturespring.controller;

import com.kim.voiturespring.dao.VoitureDao;
import com.kim.voiturespring.dto.VoitureDto;
import com.kim.voiturespring.service.VoitureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carSpring")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    public ResponseEntity<List<VoitureDao>> getAllTests() {
        return voitureService.getAllVoitures();
    }

    @PostMapping
    public ResponseEntity<VoitureDto>createVoiture(@RequestBody VoitureDto voitureDto) {
        VoitureDto voiture = voitureService.createVoiture(voitureDto);
        return ResponseEntity.ok(voiture);
    }

    //Récupération d'une voiture par id
    @GetMapping("/{id}")
    public ResponseEntity<VoitureDao> getVoitureById(
            @PathVariable UUID id
    ) {
        VoitureDao voitureById = voitureService.getVoitureById(id);
        return ResponseEntity.ok(voitureById);
    }


}
