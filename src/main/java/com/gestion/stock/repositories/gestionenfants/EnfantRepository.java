package com.gestion.stock.repositories.gestionenfants;

import com.gestion.stock.entities.gestionenfants.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {

    @Query("select max (e.id) from Enfant e")
    Long getMaxId();

    Optional<Enfant> findByMatricule(String matricule);
}
