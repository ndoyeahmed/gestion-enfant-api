package com.gestion.stock.repositories.gestionenfants;

import com.gestion.stock.entities.gestionenfants.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {

    Optional<Dossier> findByCode(String code);
}
