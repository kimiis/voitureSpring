package com.kim.voiturespring.repository;

import com.kim.voiturespring.dao.VoitureDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class VoitureRepository {

    private final JdbcTemplate jdbcTemplate;

    public VoitureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Récupérer toutes les voitures
    public List<VoitureDao> findAll() {
        String query = "SELECT * FROM voiture";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                VoitureDao.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .modele(rs.getString("modele"))
                        .marque(rs.getString("marque"))
                        .annee(rs.getInt("annee"))
                        .nbPorte(rs.getInt("nbPorte"))
                        .explosiveAirbag(rs.getBoolean("explosiveAirbag"))
                        .build()
        );
    }

    // Trouver un test par ID
    public VoitureDao findById(UUID idVoiture) {
        String query = "SELECT * FROM voiture WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{idVoiture}, (rs, rowNum) ->
                VoitureDao.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .modele(rs.getString("modele"))
                        .marque(rs.getString("marque"))
                        .nbPorte(rs.getInt("nbPorte"))
                        .explosiveAirbag(rs.getBoolean("explosiveAirbag"))
                        .build()
        );
    }

    //Créer une voiture
    public UUID createVoiture(VoitureDao voiture) {
        String query = "INSERT INTO voiture (modele, marque, nbPorte, annee, explosiveAirbag) VALUES (?, ?, ?, ?, ?) RETURNING id";

        return jdbcTemplate.queryForObject(query, new Object[]{
                voiture.getModele(),
                voiture.getMarque(),
                voiture.getNbPorte(),
                voiture.getAnnee(),
                voiture.isExplosiveAirbag()
        }, UUID.class);
    }


    //  Mettre à jour complètement un test
    public VoitureDao updateVoiture(UUID idVoiture, VoitureDao voiture) {
        String query = "UPDATE voiture SET modele = ?, marque = ?, annee = ?, nbPorte = ?, explosiveAirbag = ? WHERE id = ?";

        int rowsUpdated = jdbcTemplate.update(query,
                voiture.getModele(),
                voiture.getMarque(),
                voiture.getAnnee(),
                voiture.getNbPorte(),
                voiture.isExplosiveAirbag(),
                idVoiture
        );

        if (rowsUpdated > 0) {
            return findById(idVoiture);
        } else {
            throw new RuntimeException("Voiture non trouvée avec l'ID : " + idVoiture);
        }
    }

    //  Modifier partiellement un test
    public VoitureDao patchVoiture(UUID idVoiture, String column, Object value) {
        // Vérification des colonnes autorisées (sécurité contre injection SQL)
        List<String> allowedColumns = List.of("modele", "marque", "annee", "nbPorte", "explosiveAirbag");
        if (!allowedColumns.contains(column)) {
            throw new IllegalArgumentException("Colonne invalide : " + column);
        }

        String query = "UPDATE voiture SET " + column + " = ? WHERE id = ?";

        int rowsUpdated = jdbcTemplate.update(query, value, idVoiture);

        if (rowsUpdated > 0) {
            return findById(idVoiture);
        } else {
            throw new RuntimeException("Voiture non trouvée avec l'ID : " + idVoiture);
        }
    }

    //  Supprimer un test
    public boolean deleteTest(UUID idVoiture) {
        String query = "DELETE FROM voiture WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(query, idVoiture);

        if (rowsDeleted > 0) {
            return true;
        } else {
            throw new RuntimeException("Voiture non trouvé avec l'ID : " + idVoiture);
        }
    }

}
