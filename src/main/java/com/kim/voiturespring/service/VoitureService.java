package com.kim.voiturespring.service;

import com.kim.voiturespring.dao.VoitureDao;

import com.kim.voiturespring.dto.VoitureDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoitureService {

    private final List<VoitureDao> voitures;

    public VoitureService(List<VoitureDao> voitures) {
        this.voitures = voitures;
    }

    //Fonction pour créer une voiture
    public VoitureDto createVoiture(VoitureDto voitureDto) {
        VoitureDao voiture = new VoitureDao(
                UUID.randomUUID(),
                voitureDto.marque(),
                voitureDto.modele(),
                voitureDto.annee(),
                voitureDto.nbPorte(),
                voitureDto.explosiveAirbag()
        );

        VoitureDao.VoitureDaoBuilder builder = VoitureDao.builder()
                .id(voiture.getId())
                .marque(voitureDto.marque())
                .modele(voitureDto.modele())
                .annee(voitureDto.annee());

        if (voitureDto.nbPorte() > 0) {
            builder.nbPorte(voitureDto.nbPorte());
        }

        if (voitureDto.explosiveAirbag() == true) {
            builder.explosiveAirbag(voitureDto.explosiveAirbag());
        }

        VoitureDao voitureFinale = builder.build();

        voitures.add(voitureFinale);

        return new VoitureDto(
                voitureFinale.getId(),
                voitureFinale.getMarque(),
                voitureFinale.getModele(),
                voitureFinale.getAnnee(),
                voitureFinale.getNbPorte(),
                voitureFinale.isExplosiveAirbag()
        );
    }

    //Récupération de toutes les voitures
    public ResponseEntity<List<VoitureDao>> getAllVoitures() {
        return ResponseEntity.ok(voitures);
    }

    //Récupération d'une voiture par id
    public VoitureDao getVoitureById(UUID id) {
        Optional<VoitureDao> first = voitures.stream()
                .filter(voiture -> voiture.getId().equals(id))
                .findFirst();

        if (first.isEmpty()) {
            throw new RuntimeException("Voiture not found");
        }
        return first.get();
    }

}
