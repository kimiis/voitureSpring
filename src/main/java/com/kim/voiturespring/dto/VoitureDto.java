package com.kim.voiturespring.dto;

import java.util.UUID;

public record VoitureDto(
        UUID id,
        String marque,
        String modele,
        int annee,
        int nbPorte,
        boolean explosiveAirbag
) {
}
