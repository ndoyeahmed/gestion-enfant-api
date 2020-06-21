package com.gestion.stock.repositories.sales;

import com.gestion.stock.entities.sales.LineCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineCommandeRepository extends JpaRepository<LineCommande, Long> {
}
