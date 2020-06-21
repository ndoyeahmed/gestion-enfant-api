package com.gestion.stock.repositories.stock;

import com.gestion.stock.entities.stock.LineMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineMouvementRepository extends JpaRepository<LineMouvement, Long> {
}
