package com.gestion.stock.repositories.stock;

import com.gestion.stock.entities.stock.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {
}
