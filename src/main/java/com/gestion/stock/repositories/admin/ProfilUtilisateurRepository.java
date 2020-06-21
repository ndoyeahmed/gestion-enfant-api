package com.gestion.stock.repositories.admin;

import com.gestion.stock.entities.admin.Profil;
import com.gestion.stock.entities.admin.ProfilUtilisateur;
import com.gestion.stock.entities.admin.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilUtilisateurRepository extends JpaRepository<ProfilUtilisateur, Long> {
    Optional<ProfilUtilisateur> findByUtilisateurAndProfil(Utilisateur utilisateur, Profil profil);
}
