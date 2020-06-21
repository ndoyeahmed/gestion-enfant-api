package com.gestion.stock.repositories.sales;

import com.gestion.stock.entities.sales.LineVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineVenteRepository extends JpaRepository<LineVente, Long> {
}
