package com.gestion.stock.repositories.sales;

import com.gestion.stock.entities.sales.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
}
