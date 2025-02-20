package com.kim.voiturespring.dao;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder

@AllArgsConstructor

public class VoitureDao {
    private UUID id;
    private String marque;
    private String modele;
    private int annee;
    private int nbPorte;
    private boolean explosiveAirbag;
}
