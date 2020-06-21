package com.gestion.stock.repositories.gestionenfants;

import com.gestion.stock.entities.admin.Utilisateur;
import com.gestion.stock.entities.gestionenfants.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByCode(String code);

    Optional<List<Site>> findAllByUtilisateurAndArchive(Utilisateur utilisateur, Boolean archive);

    Optional<List<Site>> findAllByArchive(Boolean archive);
}
