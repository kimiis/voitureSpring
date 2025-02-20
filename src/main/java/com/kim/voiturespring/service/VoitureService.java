package com.kim.voiturespring.service;

import com.kim.voiturespring.dao.VoitureDao;

import com.kim.voiturespring.dto.VoitureDto;
import com.kim.voiturespring.exception.VoitureNotFoundException;
import com.kim.voiturespring.repository.VoitureRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class VoitureService {

    private final List<VoitureDao> voitures;
    private final VoitureRepository voitureRepository;

    public VoitureService(List<VoitureDao> voitures, VoitureRepository voitureRepository) {
        this.voitures = voitures;
        this.voitureRepository = voitureRepository;
    }

    //Récupération de toutes les voitures
    public List<VoitureDao> getAllVoitures() {
        return voitureRepository.findAll();
    }

    //Récupération d'une voiture par id
    public VoitureDao getVoitureById(UUID id) {
        return voitureRepository.findById(id);
    }
//    public VoitureDao getVoitureById(UUID id) {
//        Optional<VoitureDao> first = voitures.stream()
//                .filter(voiture -> voiture.getId().equals(id))
//                .findFirst();
//
//        if (first.isEmpty()) {
//            throw new RuntimeException("Voiture not found");
//        }
//        return first.get();
//    }

    //Fonction pour créer une voiture
    public VoitureDao createVoiture(VoitureDto voitureDto) {
        //génération d'une UUID si null
        UUID newId = (voitureDto.id() != null) ? voitureDto.id() : UUID.randomUUID();

        UUID id = voitureRepository.createVoiture(new VoitureDao(
                newId,
                voitureDto.marque(),
                voitureDto.modele(),
                voitureDto.annee(),
                voitureDto.nbPorte(),
                voitureDto.explosiveAirbag()
        ));
        return voitureRepository.findById(id);
    }

//    public VoitureDto createVoiture(VoitureDto voitureDto) {
//        VoitureDao voiture = new VoitureDao(
//                UUID.randomUUID(),
//                voitureDto.marque(),
//                voitureDto.modele(),
//                voitureDto.annee(),
//                voitureDto.nbPorte(),
//                voitureDto.explosiveAirbag()
//        );
//
//        VoitureDao.VoitureDaoBuilder builder = VoitureDao.builder()
//                .id(voiture.getId())
//                .marque(voitureDto.marque())
//                .modele(voitureDto.modele())
//                .annee(voitureDto.annee());
//
//        if (voitureDto.nbPorte() > 0) {
//            builder.nbPorte(voitureDto.nbPorte());
//        }
//
//        if (voitureDto.explosiveAirbag() == true) {
//            builder.explosiveAirbag(voitureDto.explosiveAirbag());
//        }
//
//        VoitureDao voitureFinale = builder.build();
//
//        voitures.add(voitureFinale);
//
//        return new VoitureDto(
//                voitureFinale.getId(),
//                voitureFinale.getMarque(),
//                voitureFinale.getModele(),
//                voitureFinale.getAnnee(),
//                voitureFinale.getNbPorte(),
//                voitureFinale.isExplosiveAirbag()
//        );
//    }

    //Update d'une voiture
    public VoitureDao updateVoiture(UUID id, VoitureDto voitureDto) {
        getVoitureById(id);

        VoitureDao voitureUpdated = new VoitureDao(
                id,
                voitureDto.marque(),
                voitureDto.modele(),
                voitureDto.annee(),
                voitureDto.nbPorte(),
                voitureDto.explosiveAirbag()
        );

        return voitureRepository.updateVoiture(id, voitureUpdated);
    }

//    public VoitureDao updateVoiture(VoitureDto voitureDto, UUID id) {
//        VoitureDao voitureById = voitures.stream()
//                .filter(voiture -> voiture.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new VoitureNotFoundException("Voiture not found"));
//
//        voitureById.setModele(voitureDto.modele());
//        voitureById.setMarque(voitureDto.marque());
//        voitureById.setAnnee(voitureDto.annee());
//        voitureById.setNbPorte(voitureDto.nbPorte());
//        voitureById.setExplosiveAirbag(voitureDto.explosiveAirbag());
//
//        return voitureById;
//    }

    //Update partiellement une voiture
    public VoitureDao patchVoiture(UUID id, String column, Object value) {
        getVoitureById(id);
        return voitureRepository.patchVoiture(id, column, value);
    }
    //Delete une voiture
    public boolean deleteVoiture(UUID id) {
        getVoitureById(id);
        return voitureRepository.deleteTest(id);
    }
//    public VoitureDao deleteVoiture(UUID id) {
//        VoitureDao voitureById = voitures.stream()
//                .filter(voitureDao -> voitureDao.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Voiture not found"));
//        voitures.remove(voitureById);
//
//        return voitureById;
//    }


}
