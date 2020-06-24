package com.gestion.stock.repositories.gestionenfants;

import com.gestion.stock.entities.admin.Utilisateur;
import com.gestion.stock.entities.gestionenfants.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {

    @Query("select max (d.id) from Dossier d")
    Long getMaxId();

    Optional<Dossier> findByCode(String code);

    Optional<List<Dossier>> findAllByArchiveOrderByCreatedDateDesc(boolean archive);

    Optional<List<Dossier>> findAllByArchiveAndEnfant_Site_Utilisateur(boolean archive, Utilisateur utilisateur);
}
